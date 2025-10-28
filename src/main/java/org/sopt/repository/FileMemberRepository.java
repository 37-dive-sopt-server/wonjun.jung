package org.sopt.repository;

import org.sopt.domain.Member;
import org.springframework.stereotype.Repository;

import java.io.*;
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(store);
        } catch (IOException e) {
            e.printStackTrace();
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
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                store = new HashMap<>();
            }
        } else  {
            store = new HashMap<>();
        }
    }
}
