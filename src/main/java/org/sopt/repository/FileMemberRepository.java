package org.sopt.repository;

import org.sopt.common.ErrorCode;
import org.sopt.domain.Member;
import org.sopt.exception.BusinessException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Repository("fileMemberRepository")
public class FileMemberRepository extends MemoryMemberRepository {

    private static final String FILE_PATH = "members.dat";

    public FileMemberRepository() {
        super();
        loadDataFromFile();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveDataToFile();
            System.out.println("데이터를 파일에 저장하고 프로그램을 종료합니다.");
        }));
    }
    
    @Override
    public Member save(Member member) {
        Member savedMember = super.save(member);
        saveDataToFile();
        return savedMember;
    }

    @Override
    public Long delete(Long id) {
        Long deletedId = super.delete(id);
        saveDataToFile();
        return deletedId;
    }

    @Override
    public Member update(Member member) {
        Member updatedMember = super.update(member);
        saveDataToFile();
        return updatedMember;
    }

    private void saveDataToFile() {
        File tempFile = new File(FILE_PATH + ".tmp");
        File actualFile = new File(FILE_PATH);

        // 임시 파일에 데이터 쓰기
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            oos.writeObject(store);
            System.out.println("데이터가 임시 파일에 성공적으로 저장되었습니다: " + tempFile.getName());
        } catch (FileNotFoundException e) {
            System.err.println("임시 파일 저장 실패: 파일 경로를 찾을 수 없습니다 - " + tempFile.getPath());
            e.printStackTrace();
            tempFile.delete();  // 실패 시 임시 파일 삭제
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        } catch (IOException e) {
            System.err.println("임시 파일 저장 실패: IO 오류 발생 - " + e.getMessage());
            e.printStackTrace();
            tempFile.delete();  // 실패 시 임시 파일 삭제
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        // 임시 파일을 실제 파일로 이동
        try {
            Files.move(tempFile.toPath(), actualFile.toPath(),
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("데이터가 파일에 성공적으로 저장되었습니다: " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("파일 이동 실패: " + e.getMessage());
            e.printStackTrace();
            tempFile.delete();  // 실패 시 임시 파일 삭제
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }
    }

    private void loadDataFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                store = (Map<Long, Member>) ois.readObject();
                if (!store.isEmpty()) {
                    sequence = store.keySet().stream()
                            .max(Long::compareTo)
                            .orElse(0L);
                }
                System.out.println("파일에서 데이터를 성공적으로 불러왔습니다: " + FILE_PATH + " (회원 수: " + store.size() + ")");
            } catch (FileNotFoundException e) {
                System.err.println("파일 로드 실패: 파일을 찾을 수 없습니다 - " + FILE_PATH);
                e.printStackTrace();
                store = new HashMap<>();
                System.out.println("빈 데이터로 초기화합니다.");
            } catch (IOException e) {
                System.err.println("파일 로드 실패: 파일을 읽는 중 오류 발생 - " + e.getMessage());
                e.printStackTrace();
                store = new HashMap<>();
                System.out.println("빈 데이터로 초기화합니다.");
            } catch (ClassNotFoundException e) {
                System.err.println("파일 로드 실패: 데이터 형식이 올바르지 않습니다 - " + e.getMessage());
                e.printStackTrace();
                store = new HashMap<>();
                System.out.println("빈 데이터로 초기화합니다.");
            }
        } else {
            store = new HashMap<>();
            System.out.println("저장된 파일이 없습니다. 빈 데이터로 시작합니다.");
        }
    }
}
