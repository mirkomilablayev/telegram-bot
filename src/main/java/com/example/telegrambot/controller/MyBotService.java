package com.example.telegrambot.controller;

import com.example.telegrambot.configuration.BotConfiguration;
import com.example.telegrambot.dto.BookersDto;
import com.example.telegrambot.entity.*;
import com.example.telegrambot.exception.UserNotFoundException;
import com.example.telegrambot.repository.*;
import com.example.telegrambot.utils.Constant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MyBotService extends TelegramLongPollingBot {


    private final BotConfiguration botConfiguration;
    private final UserRepository userRepository;
    private final CourseRepo courseRepo;
    private final LinkRepo linkRepo;
    private final BookingToCourseRepo bookingToCourseRepo;
    private final UserRoleRepo roleRepo;


    @Override
    public String getBotUsername() {
        return this.botConfiguration.getUsername();
    }

    @Override
    public String getBotToken() {
        return this.botConfiguration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = getChatId(update);
        User currentUser = getCurrentUser(update, chatId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        String text = getText(update);
        String calBackData = getCalBackData(update);

        if (!isBlank(currentUser.getPhoneNumber()) && (text.equalsIgnoreCase("/start") || text.equalsIgnoreCase("Ortga"))) {
            currentUser.setStatus(Constant.main_menu);
            currentUser = userRepository.save(currentUser);
        }


        if (text.equalsIgnoreCase("Admin Panel")) {
            currentUser.setIsUser(false);
            currentUser.setStatus(Constant.admin_main_menu);
            currentUser = userRepository.save(currentUser);
        } else if (text.equalsIgnoreCase("User Panel")) {
            currentUser.setIsUser(true);
            currentUser.setStatus(Constant.main_menu);
            currentUser = userRepository.save(currentUser);
        }


        if (currentUser.getIsUser()) {
            if (!isBlank(currentUser.getPhoneNumber()) &&
                    (text.equalsIgnoreCase("Bizning kurslar")
                            || text.equalsIgnoreCase("Kursga Yozilish"))) {
                currentUser = userRepository.save(currentUser);
                sendMessage.setReplyMarkup(getCoursesButton());
                if (text.equalsIgnoreCase("Bizning kurslar"))
                    sendMessage.setText("Bizning kurslar");
                else
                    sendMessage.setText("Kurslardan birini tanlang");
                currentUser.setStatus(Constant.our_courses);
                userRepository.save(currentUser);
            }

            Optional<Course> courseOptional = courseRepo.findByCourseName(text);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                File file = new File(course.getFileId());


                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setCaption(course.getCourseName() + "\n\n" + course.getCourseDefinition());
                sendPhoto.setPhoto(new InputFile(file));

                if (!bookingToCourseRepo.existsByStudentAndCourse(currentUser, course)) {
                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText("Kursga yozilish");
                    button.setCallbackData(currentUser.getId() + ":" + course.getId());
                    rowInline.add(button);
                    rowsInline.add(rowInline);
                    markupInline.setKeyboard(rowsInline);
                    sendPhoto.setReplyMarkup(markupInline);
                }
                try {
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (!isBlank(currentUser.getPhoneNumber()) && text.equalsIgnoreCase("Kursga Yozilish")) {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText("Telegram orqali");
                inlineKeyboardButton.setUrl("t.me/ris_mamuriyat");
                rowInline.add(inlineKeyboardButton);
                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);
                sendMessage.setReplyMarkup(markupInline);
                sendMessage.setText("+998333003222\nRaqamiga qo'ng'iroq qiling va " + "Malumotlaringizni qoldiring. yoki quyidagi " + "link orqali murojaat qiling");
            }


            if (!isBlank(currentUser.getPhoneNumber()) &&
                    currentUser.getStatus().equalsIgnoreCase(Constant.main_menu)) {
                sendMessage.setReplyMarkup(get_main_menu(currentUser));
                sendMessage.setText("Asosiy Menyu");
            }

            if (currentUser.getStatus().equals(Constant.step2)) {
                text = text.startsWith("+") ? text : "+" + text;
                currentUser.setPhoneNumber(text);
                if (currentUser.getPhoneNumber().equalsIgnoreCase("+998945331738")) {
                    UserRole userRole = roleRepo.findByName(Constant.admin).get();
                    List<UserRole> userRoles = currentUser.getUserRoles();
                    userRoles.add(userRole);
                    currentUser.setUserRoles(userRoles);
                }
                currentUser.setStatus(Constant.main_menu);
                currentUser = userRepository.save(currentUser);
                ReplyKeyboardMarkup main_menu = get_main_menu(currentUser);
                sendMessage.setReplyMarkup(main_menu);
                sendMessage.setText("Asosiy Menyu");
            }


            if (isBlank(currentUser.getPhoneNumber()) && currentUser.getStatus().equals(Constant.step1)) {
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setResizeKeyboard(true);
                keyboardMarkup.setSelective(true);
                List<KeyboardRow> keyboardRowList = new ArrayList<>();
                KeyboardRow row1 = new KeyboardRow();
                KeyboardButton share_contact = new KeyboardButton("Share Contact");
                share_contact.setRequestContact(true);
                row1.add(share_contact);
                keyboardRowList.add(row1);
                keyboardMarkup.setKeyboard(keyboardRowList);
                sendMessage.setText("Bizga telefon raqamingizni yuboring");
                sendMessage.setReplyMarkup(keyboardMarkup);
                currentUser.setStatus(Constant.step2);
                userRepository.save(currentUser);
            }

            if (text.equals("Kontaktlarimiz")) {
                getSocialMediaInformations(sendMessage);
            }


            if (!isBlank(calBackData)) {
                if (!calBackData.startsWith("data")) {
                    String user_id = "";
                    String course_id = "";
                    boolean flag = false;
                    for (int i = 0; i < calBackData.length(); i++) {
                        String a = calBackData.substring(i, i + 1);
                        if (a.equals(":")) {
                            i++;
                            a = calBackData.substring(i, i + 1);
                            flag = true;
                        }
                        if (!flag)
                            user_id = user_id + a;
                        else
                            course_id = course_id + a;
                    }
                    User user = userRepository.findById(Long.parseLong(user_id)).get();
                    Course course = courseRepo.findById(Long.parseLong(course_id)).get();
                    BookingToCourse bookingToCourse = new BookingToCourse();
                    bookingToCourse.setCourse(course);
                    bookingToCourse.setStudent(user);
                    bookingToCourse.setBookedAt(LocalDateTime.now());
                    if (!bookingToCourseRepo.existsByStudentAndCourse(user, course))
                        bookingToCourseRepo.save(bookingToCourse);
                    sendMessage.setText("Siz Ushbu kursga ro'yxatdan o'tdingiz va tez kunda Administratorlarimiz siz bilan bog'lanishadi!!!");
                }
            }
        } else {
            if (currentUser.getStatus().equalsIgnoreCase(Constant.admin_main_menu)) {
                sendMessage.setText("Admin Panelga xush kelibsiz");
                sendMessage.setReplyMarkup(getAdminMenu());
            }

            if (text.equalsIgnoreCase("Statistika")) {
                sendMessage.setText(getStatistics());
                currentUser.setStatus(Constant.admin_statistic);
                currentUser = userRepository.save(currentUser);
            }

            if (text.equalsIgnoreCase("Kursga yozilganlar")) {
                List<BookingToCourse> booking = bookingToCourseRepo.findAll();
                bookingToCourseRepo.deleteAll();
                PdfTicketMaker(booking);
                File file = new File("src/main/resources/pdfList.pdf");
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(currentUser.getChatId());
                sendDocument.setDocument(new InputFile(file));
                sendDocument.setCaption("Bu yerda hamma kursga yozilganlar ro'yxati");
                currentUser.setStatus(Constant.wrote_courses);
                userRepository.save(currentUser);
                try {
                    execute(sendDocument);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }

        }
        try {
            if (!isBlank(sendMessage.getText())) {
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private String getStatistics() {
        int counter = 0;
        String res2 = "";
        for (Course course : courseRepo.findAll()) {
            int i = bookingToCourseRepo.countAllByCourse(course);
            res2 = res2 + course.getCourseName() + " - " + i + "\n";
            counter = counter + i;
        }
        String res = "Hamma Azolar - " + userRepository.count() + "\n\n" + res2 + "\nKursga Yozilmaganlar - " + userRepository.getNonFollowersCount();
        return res;
    }

    private ReplyKeyboard getAdminMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Statistika"));
        row1.add(new KeyboardButton("Kursga yozilganlar"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("User Panel"));
        keyboardRowList.add(row1);
        keyboardRowList.add(row3);
        markup.setKeyboard(keyboardRowList);
        return markup;
    }

    private ReplyKeyboardMarkup get_main_menu(User currentUser) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Bizning kurslar"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Kontaktlarimiz"));
        row2.add(new KeyboardButton("Kursga Yozilish"));
        keyboardRowList.add(row1);
        keyboardRowList.add(row2);
        if (currentUser.getUserRoles().size() == 2) {
            KeyboardRow row3 = new KeyboardRow();
            row3.add(new KeyboardButton("Admin Panel"));
            keyboardRowList.add(row3);
        }
        markup.setKeyboard(keyboardRowList);
        return markup;
    }


    private void getSocialMediaInformations(SendMessage sendMessage) {
        List<Link> all = linkRepo.findAll();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (Link link : all) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(link.getName());
            inlineKeyboardButton.setUrl(link.getLink());
            rowInline.add(inlineKeyboardButton);
            rowsInline.add(rowInline);
            // Add it to the message
            markupInline.setKeyboard(rowsInline);
        }
        sendMessage.setReplyMarkup(markupInline);
        sendMessage.setText("Bizni Ijtimoiy tarmoqlarda ham kuzatib boring");
    }


    private ReplyKeyboard getCoursesButton() {
        List<Course> all = courseRepo.findAll();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        boolean flag = false;
        for (int i = 0; i < all.size(); i++) {
            KeyboardRow keyboardButtons = new KeyboardRow();
            if (i != (all.size() - 1)) {
                keyboardButtons.add(new KeyboardButton(all.get(i).getCourseName()));
                keyboardButtons.add(new KeyboardButton(all.get(i + 1).getCourseName()));
                i = i + 1;
            } else {
                keyboardButtons.add("Ortga");
                keyboardButtons.add(new KeyboardButton(all.get(i).getCourseName()));
                flag = true;
            }
            keyboardRowList.add(keyboardButtons);
        }
        KeyboardRow row1 = new KeyboardRow();
        if (!flag)
            row1.add(new KeyboardButton("Ortga"));
        keyboardRowList.add(row1);
        keyboardMarkup.setKeyboard(keyboardRowList);
        return keyboardMarkup;
    }

    public String getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        } else return "";
    }


    public User getCurrentUser(Update update, String chatId) {
        Optional<User> userOptional = userRepository.findByChatId(chatId);
        try {
            return userOptional.orElseThrow(UserNotFoundException::new);
        } catch (UserNotFoundException e) {
            User user = new User();
            Message message = update.getMessage();
            user.setRealId(message.getFrom().getId().toString());
            user.setChatId(chatId);
            user.setFistName(message.getFrom().getFirstName());
            user.setUserRoles(new ArrayList<>(
                    Arrays.asList(
                            roleRepo.findByName(Constant.user).get()
                    )));
            user.setStatus(Constant.step1);
            return userRepository.save(user);
        }
    }


    private String getText(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.hasText()) return message.getText();
                else if (message.hasContact()) return message.getContact().getPhoneNumber();
                else return "  ";
            } else
                return "  ";
        } catch (Exception e) {
            return "  ";
        }
    }

    private String getCalBackData(Update update) {
        try {
            return update.getCallbackQuery().getData();
        } catch (Exception e) {
            return "  ";
        }
    }

    public Boolean isBlank(String str) {
        if (str != null)
            return str.trim().isEmpty();
        else
            return false;
    }


    public void PdfTicketMaker(List<BookingToCourse> booking) {
        try {
            PdfWriter writer = new PdfWriter("src/main/resources/pdfList.pdf");
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.addNewPage();
            List<BookersDto> bookersDtos = makeBookers(booking);
            Document document = new Document(pdfDocument);
            for (BookersDto bookersDto : bookersDtos) {
                document.add(new Paragraph(bookersDto.getFullName()));
                for (String cours : bookersDto.getCourses()) {
                    document.add(new Paragraph(cours));
                }
                document.add(new Paragraph("---------------------"));
            }
            document.close();
            System.out.println("PDF Created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private List<BookersDto> makeBookers(List<BookingToCourse> booking) {
        List<BookersDto> res = new ArrayList<>();
        for (BookingToCourse item : booking) {
            BookersDto bookersDto = new BookersDto();
            boolean flag = false;
            for (BookersDto re : res) {
                if (re.getStudent_id().equals(item.getStudent().getId())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                bookersDto.setCourses(getCourses(booking, item.getStudent().getId()));
                bookersDto.setFullName("Name - " + item.getStudent().getFistName() + ", Phone-" + item.getStudent().getPhoneNumber());
                bookersDto.setStudent_id(item.getStudent().getId());
                res.add(bookersDto);
            }
        }
        return res;
    }

    private List<String> getCourses(List<BookingToCourse> booking, Long id) {
        List<String> course = new ArrayList<>();
        for (BookingToCourse i : booking) {
            if (i.getStudent().getId().equals(id)) {
                String str = "";
                str = str + i.getCourse().getCourseName();
                course.add(str);
            }
        }

        return course;
    }


}
