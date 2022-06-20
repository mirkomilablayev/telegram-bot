package com.example.telegrambot.configuration;



import com.example.telegrambot.controller.MyBotService;
import com.example.telegrambot.entity.Course;
import com.example.telegrambot.entity.Link;
import com.example.telegrambot.entity.UserRole;
import com.example.telegrambot.repository.CourseRepo;
import com.example.telegrambot.repository.LinkRepo;
import com.example.telegrambot.repository.UserRepository;
import com.example.telegrambot.repository.UserRoleRepo;
import com.example.telegrambot.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;



@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final MyBotService myBotService;
    private final UserRepository userRepository;
    private final CourseRepo courseRepo;
    private final LinkRepo linkRepo;
    private final UserRoleRepo userRoleRepo;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this.myBotService);

        if (ddl.equalsIgnoreCase("create")
                || ddl.equalsIgnoreCase("create-drop")){

            courseRepo.save(new Course("Foundation","src/main/resources/Course/img.png","" +
                    "Foundation kursi asosan dasturlashga endi kirgan " +
                    "o'quvchilar uchun mo'ljallangan bo'lib uning davomiyligi 3 oydir, " +
                    "Foundation kursida biz ko'plab tillarning otasi bo'lmish C++ dasturlash tilidan foydalanamiz"));

            courseRepo.save(new Course("Java Backend","src/main/resources/Course/img.png"
                    ,"Java Backend yunalishi 7 oy davom etadi"));

            courseRepo.save(new Course("Frontend","src/main/resources/Course/img.png"
                    ,"Frontend haqida"));

            courseRepo.save(new Course("Python Backend","src/main/resources/Course/img.png"
                    ,"Python backend"));

            courseRepo.save(new Course("Android","src/main/resources/Course/img.png"
                    ,"Android haqida"));

            courseRepo.save(new Course("Grafik Dizayn","src/main/resources/Course/img.png"
                    ,"Grafik dizayn haqida"));


            courseRepo.save(new Course("UX/UI Dezayn","src/main/resources/Course/img.png"
                    ,"Web dezayn haqida"));

            courseRepo.save(new Course("Flutter","src/main/resources/Course/img.png"
                    ,"Flutter haqida"));

            courseRepo.save(new Course("SMM","src/main/resources/Course/img.png"
                    ,"SMM Haqida"));

            linkRepo.save(new Link("https://www.instagram.com/rakhmonovitschool/","Instagram"));
            linkRepo.save(new Link("https://www.facebook.com/rakhmonovitschool","Facebook"));
            linkRepo.save(new Link("t.me/RakhmonovITSchool","Telegram"));

            userRoleRepo.save(new UserRole(Constant.admin));
            userRoleRepo.save(new UserRole(Constant.user));

        }



    }




}
