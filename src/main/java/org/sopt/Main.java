package org.sopt;

import org.sopt.controller.MemberController;
import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.repository.FileMemberRepository;
import org.sopt.repository.MemoryMemberRepository;
import org.sopt.service.MemberServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        FileMemberRepository memberRepository = new FileMemberRepository();
        MemberServiceImpl memberService = new MemberServiceImpl(memberRepository);
        MemberController memberController = new MemberController(memberService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️. 회원 삭제 📋");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("등록할 회원 이름을 입력하세요: ");
                    String name = scanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("⚠️ 이름을 입력해주세요.");
                        continue;
                    }

                    System.out.print("등록할 회원의 생년원일을 입력하세요(ex.2002-03-14): ");
                    LocalDate birthDate;
                    try {
                        String dateString = scanner.nextLine();
                        birthDate = LocalDate.parse(dateString);
                    } catch (DateTimeParseException e) {
                        System.out.println("⚠️ 양식에 맞는 생년월일을 입력해주세요.(ex.2002-03-14)");
                        continue;
                    }

                    System.out.print("등록할 회원의 이메일을 입력하세요: ");
                    String email = scanner.nextLine();
                    if (email.trim().isEmpty()) {
                        System.out.println("⚠️ 이메일을 입력해주세요.");
                        continue;
                    }

                    System.out.print("등록할 회원의 성별을 선택하세요(남성:0, 여성:1): ");
                    String sexChoice = scanner.nextLine();
                    Sex sex;
                    if (sexChoice.equals("0") || sexChoice.equals("1")) {
                        sex = (sexChoice.equals("0")) ? Sex.MALE : Sex.FEMALE;
                    } else {
                        System.out.println("⚠️ 유효한 성별을 선택해주세요.(남성:0, 여성:1)");
                        continue;
                    }

                    try {
                        Long createdId = memberController.createMember(name, birthDate, email, sex);
                        if (createdId != null) {
                            System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
                        } else {
                            System.out.println("❌ 회원 등록 실패");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    System.out.print("조회할 회원 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        Optional<Member> foundMember = memberController.findMemberById(id);
                        if (foundMember.isPresent()) {
                            System.out.println(
                                    "✅ 조회된 회원: ID=" + foundMember.get().getId() +
                                    ", 이름=" + foundMember.get().getName() +
                                    ", 생년월일=" + foundMember.get().getBirthDate() +
                                    ", 이메일=" + foundMember.get().getEmail() +
                                    ", 성별=" + foundMember.get().getSex()
                            );
                        } else {
                            System.out.println("⚠️ 해당 ID의 회원을 찾을 수 없습니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
                    break;
                case "3":
                    List<Member> allMembers = memberController.getAllMembers();
                    if (allMembers.isEmpty()) {
                        System.out.println("ℹ️ 등록된 회원이 없습니다.");
                    } else {
                        System.out.println("--- 📋 전체 회원 목록 📋 ---");
                        for (Member member : allMembers) {
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
                    break;
                case "4":
                    System.out.print("삭제할 회원의 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        Optional<Member> foundMember = memberController.findMemberById(id);
                        if (foundMember.isPresent()) {
                            Long deletedId = memberController.deleteMember(id);
                            if (deletedId != null) {
                                System.out.println("✅ 회원 삭제 완료 (ID: " + deletedId + ")");
                            } else {
                                System.out.println("❌ 회원 삭제 실패");
                            }
                        } else {
                            System.out.println("⚠️ 존재하지 않는 회원입니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
                    break;
                case "5":
                    System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
                    scanner.close();
                    return;
                default:
                    System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }
    }
}