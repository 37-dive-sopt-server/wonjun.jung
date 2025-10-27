package org.sopt.repository;

import org.sopt.domain.Member;
import org.sopt.exception.MemberNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class FileMemberRepository implements MemberRepository {

    private Map<Long, Member> store;
    private long sequence = 0L;
    private static final String FILE_PATH = "members.dat";

    public FileMemberRepository() {
        loadDataFromFile();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveDataToFile();
            System.out.println("데이터를 파일에 저장하고 프로그램을 종료합니다.");
        }));
    }

    @Override
    public Member save(Member member) {
        if (member.getId() == null || member.getId() == 0L) {
            member.setId(++sequence);
        }
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
                .filter(member -> member.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Long delete(Long id) {
        Member member = store.get(id);
        if (member == null) {
            throw new MemberNotFoundException(id);
        }
        store.remove(id);
        return member.getId();
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
