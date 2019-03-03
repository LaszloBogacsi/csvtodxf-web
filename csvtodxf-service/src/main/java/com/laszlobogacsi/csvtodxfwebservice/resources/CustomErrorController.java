package com.laszlobogacsi.csvtodxfwebservice.resources;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

;

@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String onError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            model.addAttribute("messageCode", statusCode);
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("messageTitle", HttpStatus.NOT_FOUND.getReasonPhrase())
                        .addAttribute("message", "The page you are looking for might have been removed had its name changed or is temporarily unavailable.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("messageTitle", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .addAttribute("message", "Oooops, something went wrong");
            } else {
                model.addAttribute("message", "Oooops, something went wrong");
            }
        }

        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
