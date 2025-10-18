package org.sopt.application;

import org.sopt.controller.MemberController;
import org.sopt.domain.Member;
import org.sopt.domain.Sex;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ValidationException;
import org.sopt.validator.MemberValidator;
import org.sopt.view.Consoleview;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MemberApplication {
    private final MemberController controller;
    private final Consoleview view;

    public MemberApplication(MemberController controller, Consoleview view) {
        this.controller = controller;
        this.view = view;
    }

    public void run() {
        while (true) {
            view.showMainMenu();
            String choice = view.getMenuChoice();

            try {
                if (!handleMenuChoice(choice)) {
                    return;
                }
            } catch (ValidationException | BusinessException e) {
                view.showErrorMessage(e.getMessage());
            } catch (Exception e) {
                view.showErrorMessage("예상치 못한 오류가 발생했습니다: " + e.getMessage());
            }
        }
    }

    private boolean handleMenuChoice(String choice) {
        switch (choice) {
            case "1":
                handleCreateMember();
                break;
            case "2":
                handleFindMemberById();
                break;
            case "3":
                handleFindAllMembers();
                break;
            case "4":
                handleDeleteMember();
                break;
            case "5":
                view.showExitMessage();
                view.close();
                return false;
            default:
                view.showInvalidMenuMessage();
        }
        return true;
    }
    
    // 회원 추가
    private void handleCreateMember() {
        String name = view.inputMemberName();

        String birthDateString = view.inputBirthDate();

        String email = inputEmailWithDuplicateCheck();

        String sexChoice = view.inputSex();

        LocalDate birthDate = MemberValidator.validateAndConvertDate(birthDateString);
        Sex sex = MemberValidator.validateAndConvertSex(sexChoice);

        Long createdId = controller.createMember(name, birthDate, email, sex);

        view.showSuccessMessage("회원 등록 완료 (ID: " + createdId + ")");
    }

    // 이메일 입력 및 중복 검증
    private String inputEmailWithDuplicateCheck() {
        while (true) {
            String email = view.inputEmail();  // View에서 형식 검증 (null, 빈 문자열)

            try {
                controller.checkEmailDuplicate(email);  // Service에서 중복 검증
                return email;  // 중복이 아니면 반환
            } catch (Exception e) {
                view.showErrorMessage(e.getMessage() + " 다시 입력해주세요.");
            }
        }
    }
    
    // ID기반 회원 검색
    private void handleFindMemberById() {
        String idString = view.inputMemberId();

        Long id = MemberValidator.validateAndConvertId(idString);

        Optional<Member> foundMember = controller.findMemberById(id);

        if (foundMember.isPresent()) {
            view.printMember(foundMember.get());
        } else {
            view.showErrorMessage("해당 ID의 회원을 찾을 수 없습니다.");
        }
    }
    
    // 전체 회원 조회
    private void handleFindAllMembers() {
        List<Member> allMembers = controller.getAllMembers();
        view.printAllMembers(allMembers);
    }
    
    // 회원 삭제
    private void handleDeleteMember() {
        String idString = view.inputDeleteMemberId();

        Long id = MemberValidator.validateAndConvertId(idString);

        Long deletedId = controller.deleteMember(id);
        view.showSuccessMessage("회원 삭제 완료 (ID: " + deletedId + ")");
    }
}
