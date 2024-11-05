package org.example.webtoonepics.test;

import io.jsonwebtoken.io.IOException;
import org.example.webtoonepics.community.service.AwsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EditorTestController {

    // editor test
    @Autowired
    private TestService testService;

    @Autowired
    private AwsFileService awsFileService;

    @Autowired
    private ImgService imageService;

    @GetMapping("/editor")
    public String editor() {
        return "editor";
    }

    @PostMapping("/save")
    public String save(TestDto testDto) throws java.io.IOException {
        testService.saveContent(testDto);
        return "redirect:/list";
    }

    @PostMapping("/image/upload")
    @ResponseBody
    private Map<String, Object>  image(MultipartRequest request) {
        Map<String, Object> responseData = new HashMap<>();

        try {

            String s3Url = imageService.imageUpload(request);

            responseData.put("uploaded", true);
            responseData.put("url", s3Url);

            return responseData;

        } catch (IOException e) {

            responseData.put("uploaded", false);

            return responseData;
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list")
    public String listPage(Model model) {

        model.addAttribute("testlist", testService.selectContent());
        return "list";
    }

    @GetMapping("/content/{id}")
    public String content(@PathVariable Long id, Model model) {
        Test test = testService.selectOneContent(id);
        model.addAttribute("content", test);

        return "content";
    }


}
