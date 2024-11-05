package org.example.webtoonepics.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public void saveContent(TestDto testDto) {

        String title = testDto.getTitle();
        String content = testDto.getContent();

        Test test = new Test();

        test.setTitle(title);
        test.setContent(content);


        testRepository.save(test);

    }

    public List<Test> selectContent() {

        return testRepository.findAll();
    }

    public Test selectOneContent(Long id) {
        return testRepository.findById(id).orElse(null);
    }
}
