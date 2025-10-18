package org.sopt.view;

import org.sopt.domain.Member;
import org.sopt.validator.MemberValidator;

import java.util.List;
import java.util.Scanner;

public class Consoleview {
    private final Scanner scanner;

    public Consoleview(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMainMenu() {
        System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
        System.out.println("---------------------------------");
        System.out.println("1️⃣. 회원 등록 ➕");
        System.out.println("2️⃣. ID로 회원 조회 🔍");
        System.out.println("3️⃣. 전체 회원 조회 📋");
        System.out.println("4️. 회원 삭제 📋");
        System.out.println("5️⃣. 종료 🚪");
        System.out.println("---------------------------------");
        System.out.print("메뉴를 선택하세요: ");
    }

    public String getMenuChoice() {
        return scanner.nextLine();
    }

    public String inputMemberName() {
        while (true) {
            System.out.print("등록할 회원 이름을 입력하세요: ");
            String name = scanner.nextLine();
            try {
                MemberValidator.validateName(name);
                return name;
            } catch (Exception e) {
                System.out.println("⚠️ " + e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }

    public String inputBirthDate() {
        while (true) {
            System.out.print("등록할 회원의 생년월일을 입력하세요(ex.2002-03-14): ");
            String dateString = scanner.nextLine();
            try {
                MemberValidator.validateAndConvertDate(dateString);
                return dateString;
            } catch (Exception e) {
                System.out.println("⚠️ " + e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }

    public String inputEmail() {
        while (true) {
            System.out.print("등록할 회원의 이메일을 입력하세요: ");
            String email = scanner.nextLine();
            try {
                MemberValidator.validateEmail(email);
                return email;
            } catch (Exception e) {
                System.out.println("⚠️ " + e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }

    public String inputSex() {
        while (true) {
            System.out.print("등록할 회원의 성별을 선택하세요(남성:0, 여성:1): ");
            String sexChoice = scanner.nextLine();
            try {
                MemberValidator.validateAndConvertSex(sexChoice);
                return sexChoice;
            } catch (Exception e) {
                System.out.println("⚠️ " + e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }

    public String inputMemberId() {
        while (true) {
            System.out.print("조회할 회원 ID를 입력하세요: ");
            String idString = scanner.nextLine();
            try {
                MemberValidator.validateAndConvertId(idString);
                return idString;
            } catch (Exception e) {
                System.out.println("⚠️ " + e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }

    public String inputDeleteMemberId() {
        while (true) {
            System.out.print("삭제할 회원의 ID를 입력하세요: ");
            String idString = scanner.nextLine();
            try {
                MemberValidator.validateAndConvertId(idString);
                return idString;
            } catch (Exception e) {
                System.out.println("⚠️ " + e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }

    public void printMember(Member member) {
        System.out.println(
                "✅ 조회된 회원: ID=" + member.getId() +
                        ", 이름=" + member.getName() +
                        ", 생년월일=" + member.getBirthDate() +
                        ", 이메일=" + member.getEmail() +
                        ", 성별=" + member.getSex()
        );
    }

    public void printAllMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("ℹ️ 등록된 회원이 없습니다.");
            return;
        }

        System.out.println("--- 📋 전체 회원 목록 📋 ---");
        for (Member member : members) {
            System.out.println(
                    "👤 ID=" + member.getId() +
                            ", 이름=" + member.getName() +
                            ", 생년월일=" + member.getBirthDate() +
                            ", 이메일=" + member.getEmail() +
                            ", 성별=" + member.getSex()
            );
        }
        System.out.println("--------------------------");
    }

    public void showSuccessMessage(String message) {
        System.out.println("✅ " + message);
    }

    public void showErrorMessage(String message) {
        System.out.println("⚠️ " + message);
    }

    public void showInvalidMenuMessage() {
        System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
    }

    public void showExitMessage() {
        System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
    }

    public void close() {
        scanner.close();
    }
}
