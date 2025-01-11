package com.Shoots.controller;

import com.Shoots.domain.Match;
import com.Shoots.domain.PaginationResult;
import com.Shoots.domain.Payment;
import com.Shoots.domain.RegularUser;
import com.Shoots.service.MatchService;
import com.Shoots.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/match")
public class MatchController {

    private final View error;
    private MatchService matchService;
    private PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(MatchController.class);


    public MatchController(MatchService matchService, PaymentService paymentService, View error) {
        this.matchService = matchService;
        this.paymentService = paymentService;
        this.error = error;
    }

    @GetMapping("/list")
    public ModelAndView matchList(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(required = false) String filter,
                                  @RequestParam(required = false) String gender,
                                  @RequestParam(required = false) String level,
                                  ModelAndView modelAndView, HttpSession session) {

        session.setAttribute("refer", "list");
        int limit = 10;
        int listCount = matchService.getListCount();

        List<Match> list = matchService.getMatchList(filter, gender, level, page, limit);


        PaginationResult result = new PaginationResult(page, limit, listCount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

        for (Match match : list) {

            int playerCount = paymentService.getPlayerCount(match.getMatch_idx());
            match.setPlayerCount(playerCount);

            String formattedDate = match.getMatch_date().format(formatter);
            match.setFormattedDate(formattedDate);

            String a = match.getMatch_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ' ' + match.getMatch_time();

            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime matchDateTime = LocalDateTime.parse(a, formatter1);

            LocalDateTime currentDateTime = LocalDateTime.now();

            LocalDateTime twoHoursBeforeMatch = matchDateTime.minusHours(2);

            boolean isMatchPast = twoHoursBeforeMatch.isBefore(currentDateTime);
            match.setMatchPast(isMatchPast);

            logger.info(">>>>>>>>>>>>>>>> isMatchPast : " + match.getMatch_idx() + " = " + match.isMatchPast());

        }

        logger.info(">>>>>>>>>>>>>>>> Search data Filter value : {}", filter);
        logger.info(">>>>>>>>>>>>>>>> Search data Gender value : {}", gender);
        logger.info(">>>>>>>>>>>>>>>> Search data Level value : {}", level);

        modelAndView.setViewName("match/matchList");
        modelAndView.addObject("page", page);
        modelAndView.addObject("maxpage", result.getMaxpage());
        modelAndView.addObject("startpage", result.getStartpage());
        modelAndView.addObject("endpage", result.getEndpage());
        modelAndView.addObject("listcount", listCount);
        modelAndView.addObject("matchList", list);
        modelAndView.addObject("limit", limit);

        return modelAndView;
    }

    @GetMapping("/write")
    public String matchWrite() {
        return "match/matchForm";
    }

    @PostMapping("/add")
    public String matchAdd(Match match) {

        matchService.insertMatch(match);
        logger.info(match.toString());

        return "redirect:/match/list";
    }

    @GetMapping("/detail")
    public ModelAndView matchDetail(HttpSession session, int match_idx, ModelAndView modelAndView, HttpServletRequest request) {

        Integer idx = (Integer) session.getAttribute("idx");

        Match match = matchService. getDetail(match_idx);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HH시 mm분");

        // 날짜 포맷
        String formattedDate = match.getMatch_date().format(formatter);
        match.setFormattedDate(formattedDate);

        // 시간 포맷
        String formattedTime = match.getMatch_time().format(formatterT);
        match.setFormattedTime(formattedTime);

        // isMatchPast
        String a = match.getMatch_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ' ' + match.getMatch_time();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime matchDateTime = LocalDateTime.parse(a, formatter1);

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime twoHoursBeforeMatch = matchDateTime.minusHours(2);

        boolean isMatchPast = twoHoursBeforeMatch.isBefore(currentDateTime);
        match.setMatchPast(isMatchPast);

        logger.info(">>>>>>>>>>>>>>>> isMatchPast : " + match.isMatchPast());

        // 신청 플레이어 수
        int playerCount = paymentService.getPlayerCount(match_idx);
        logger.info(">>>>>>>>>>>>>>>> playerCount : " + playerCount);

        // 신청 여부
        boolean hasPaid = false;
        Payment payment = null;

        if (idx != null) {
            hasPaid = paymentService.hasPaidForMatch(idx, match_idx);
            logger.info(">>>>>>>>>>>>>>>> hasPaid : " + hasPaid);
        }

        if (hasPaid) {
            payment = paymentService.getPaymentInfoById(idx, match_idx);
            logger.info(">>>>>>>>>>>>>>>> payment : " + payment.toString());
        }

        if (match == null) {
            logger.info("상세보기 실패");

            modelAndView.setViewName("error/error");
            modelAndView.addObject("url", request.getRequestURL());
            modelAndView.addObject("message", "상세보기 실패");
        } else {
            logger.info("상세보기 성공");

            modelAndView.setViewName("match/matchDetail");
            modelAndView.addObject("match", match);
            modelAndView.addObject("hasPaid", hasPaid);
            modelAndView.addObject("payment", payment);
            modelAndView.addObject("playerCount", playerCount);
        }
        return modelAndView;
    }

    @GetMapping("/updateForm")
    public ModelAndView matchUpdateForm(int match_idx, ModelAndView modelAndView, HttpServletRequest request) {
        Match match = matchService. getDetail(match_idx);

        if(match == null) {
            logger.info("수정 보기 실패");
            modelAndView.setViewName("error/error");
            modelAndView.addObject("url", request.getRequestURL());
            modelAndView.addObject("message", "수정보기 실패입니다");
        } else {
            logger.info("(수정)상세보기 성공");
            modelAndView.addObject("match", match);
            modelAndView.setViewName("match/matchUpdateForm");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String matchUpdate(Match match, Model model, HttpServletRequest request, RedirectAttributes rattr) {

        int result = matchService.updateMatch(match);

        System.out.println(">>>>>>>>>>>>>>> update match data : " + match.toString());
        System.out.println(">>>>>>>>>>>>>>>> update result : " + result);
        logger.info(match.toString());

        String url = "";

        if (result == 0) {
            logger.info("게시판 수정 실패");
            model.addAttribute("url", request.getRequestURL());
            model.addAttribute("message", "게시판 수정 실패");
            url = "error/error";
        } else {
            logger.info("게시판 수정 완료");
            url = "redirect:detail";
            rattr.addAttribute("match_idx", match.getMatch_idx());
        }
        return url;
    }

    @PostMapping("/delete")
    public String matchDelete(int match_idx, RedirectAttributes rattr, HttpServletRequest request, Model model) {

        int result = matchService.deleteMatch(match_idx);

        if (result == 0) {
            logger.info("게시판 삭제 실패");
            model.addAttribute("url", request.getRequestURL());
            model.addAttribute("message", "삭제 실패");
            return "error/error";
        } else {
            logger.info("게시판 삭제 성공");
            rattr.addFlashAttribute("result", "deleteSuccess");
            return "redirect:list";
        }
    }
}
