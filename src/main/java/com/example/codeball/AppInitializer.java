package com.example.codeball;

import com.example.codeball.enums.EnrollmentStatus;
import com.example.codeball.enums.PitchType;
import com.example.codeball.enums.Role;
import com.example.codeball.models.Enrollment;
import com.example.codeball.models.Game;
import com.example.codeball.models.Pitch;
import com.example.codeball.models.User;
import com.example.codeball.repositories.EnrollmentRepository;
import com.example.codeball.repositories.GameRepository;
import com.example.codeball.repositories.PitchRepository;
import com.example.codeball.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PitchRepository pitchRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public void run(String... args) throws Exception {
        User alek = new User("aa", "alek", 1, "kot", Role.ROLE_ADMIN);
        User mati = new User("bb", "mati", 2, "siwy", Role.ROLE_ADMIN);
        User ewelina = new User("cc", "ewelina", 3, "stoj", Role.ROLE_ADMIN);
        User monika = new User("dd", "monika", 4, "sudol", Role.ROLE_ADMIN);

        Pitch bojo = new Pitch(1,"dworcowa", 8, 4, "bojo", PitchType.FIRM_GROUND);

        Enrollment enrollment1 = new Enrollment(EnrollmentStatus.YES, alek, 1);
        Enrollment enrollment2 = new Enrollment(EnrollmentStatus.YES, mati, 1);
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);

        String now = "2016-11-09 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);

        List<User> teamA = new ArrayList<>();
        teamA.add(mati);

        List<User> teamB = new ArrayList<>();
        teamB.add(alek);

        Game game = new Game(1, 120, enrollments, true, true, 1, formatDateTime, teamA, 23, teamB, 32);
        userRepository.save(alek);
        userRepository.save(mati);
        userRepository.save(ewelina);
        userRepository.save(monika);

        pitchRepository.save(bojo);

        enrollmentRepository.save(enrollment1);
        enrollmentRepository.save(enrollment2);

        gameRepository.save(game);


    }
}
