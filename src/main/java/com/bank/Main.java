//package com.bank;
//
//import com.bank.model.Account;
//import com.bank.model.User;
//import com.bank.service.AccountService;
//import com.bank.service.UserService;
//import com.bank.storage.AccountStorage;
//import com.bank.storage.UserStorage;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner =new Scanner(System.in);
//        UserStorage userStorage =new UserStorage();
//        AccountStorage accountStorage =new AccountStorage();
//        UserService userService =new UserService(userStorage);
//        AccountService accountService =new AccountService(accountStorage);
//        User currentUser = null;
//        Account currentAccount = null;
//        while (true) {
//            if (currentUser == null) {
//                System.out.println();
//                System.out.println("ONLINE BANK");
//                System.out.println("1.Регистрация");
//                System.out.println("2.Вход");
//                System.out.println("3.Выход");
//                System.out.print("Выберите: ");
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//                if (choice == 1) {
//                    System.out.println();
//                    System.out.println("РЕГИСТРАЦИЯ");
//                    System.out.print("Имя:");
//                    String name = scanner.nextLine();
//                    System.out.print("Email:");
//                    String email = scanner.nextLine();
//                    System.out.print("Пароль:");
//                    String password = scanner.nextLine();
//
//                    User user = userService.register(name, email, password);
//                    if (user != null) {
//                        System.out.println();
//                        System.out.println("Регистрация успешна!");
//                        System.out.println("Ваш ID: " + user.getId());
//                    }
//                }
//                else if (choice == 2) {
//                    System.out.println();
//                    System.out.println("ВХОД ");
//                    System.out.print("Email: ");
//                    String email = scanner.nextLine();
//                    System.out.print("Пароль: ");
//                    String password = scanner.nextLine();
//
//                    User user = userService.login(email, password);
//                    if (user!= null) {
//                        currentUser = user;
//                        currentAccount = accountService.createAccount(user);
//                        System.out.println();
//                        System.out.println("Вы успешно вошли!");
//                        System.out.println("Добро пожаловать, " + user.getName()
//                        );
//                    } else {
//                        System.out.println();
//                        System.out.println("Неверный email или пароль.");
//                    }
//                }
//                else if (choice ==3) {
//                    System.out.println();
//                    System.out.println("Программа завершена.");
//                    break;
//                }
//                else {
//                    System.out.println();
//                    System.out.println("Неверный выбор");
//                }
//            }
//            else {
//                System.out.println();
//                System.out.println("ЛИЧНЫЙ КАБИНЕТ");
//                System.out.println("Пользователь: " + currentUser.getName());
//                System.out.println("ID пользователя: " + currentUser.getId());
//                System.out.println("ID счёта: " + currentAccount.getId());
//                System.out.println();
//                System.out.println("1.Баланс");
//                System.out.println("2.Пополнить счёт");
//                System.out.println("3.Снять деньги");
//                System.out.println("4.Выйти");
//
//                System.out.print("Выберите:");
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//                if (choice ==1) {
//                    System.out.println();
//                    System.out.println("Ваш баланс:" +currentAccount.getBalance() + "T");
//                }
//                else if (choice ==2) {
//                    System.out.print("Введите сумму: ");
//                    double amount =scanner.nextDouble();
//                    scanner.nextLine();
//                    accountService.deposit(currentAccount, amount);
//                }
//
//                else if (choice == 3) {
//                    System.out.print("Введите сумму: ");
//                    double amount = scanner.nextDouble();
//                    scanner.nextLine();
//                    accountService.withdraw(currentAccount, amount);
//                }
//                else if (choice == 4) {
//                    currentUser = null;
//                    currentAccount = null;
//                    System.out.println("Вы вышли из аккаунта");
//                }
//                else {
//                    System.out.println();
//                    System.out.println("Неверный выбор");
//                }
//            }
//        }
//        scanner.close();
//    }
//}