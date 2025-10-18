package org.sopt;

import org.sopt.application.MemberApplication;
import org.sopt.controller.MemberController;
import org.sopt.repository.FileMemberRepository;
import org.sopt.repository.MemberRepository;
import org.sopt.service.MemberService;
import org.sopt.service.MemberServiceImpl;
import org.sopt.view.Consoleview;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MemberRepository memberRepository = new FileMemberRepository();
        MemberService memberService = new MemberServiceImpl(memberRepository);
        MemberController memberController = new MemberController(memberService);
        Consoleview view = new Consoleview(new Scanner(System.in));

        MemberApplication application = new MemberApplication(memberController, view);
        application.run();
    }
}