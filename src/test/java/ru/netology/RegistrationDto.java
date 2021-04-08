package ru.netology;

public class RegistrationDto {
    private String name;
    private String password;
    private String status;

    public RegistrationDto(String name, String password, String status) {
        this.name = name;
        this.password = password;
        this.status = status;
//        new RegistrationDto("vasya", "password", "active");
    }
}
