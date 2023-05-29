package ru.stepchenkov.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VideoController {
    @GetMapping("/stream")
    public String getStreamPage() {
        return "stream.html";
    }

    @GetMapping("/streams")
    public ModelAndView getVideoPage(ModelAndView mav) {
        mav.setViewName("video.html");
        return mav;
    }

//    @GetMapping(value = "/video")
//    @ResponseBody
//    public void streamVideo(HttpServletResponse response) throws IOException {
//        File file = new File("/path/to/video.mp4");
//        InputStream inputStream = new FileInputStream(file);
//        response.setContentType("video/mp4");
//        IOUtils.copy(inputStream, response.getOutputStream());
//        response.flushBuffer();
//        inputStream.close();
//    }
}
