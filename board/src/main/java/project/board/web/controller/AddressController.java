package project.board.web.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import project.board.domain.dto.Board;
import project.board.domain.dto.Member;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.service.login.LoginService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value="/members")
public class AddressController {

    private final MemberRepository memberRepository;
    private final LoginService loginService;
    private final BoardRepository boardRepository;
    @Autowired private RestTemplate restTemplate;
    @Autowired private ObjectMapper objectMapper;

    @Autowired
    public AddressController(MemberRepository memberRepository, LoginService loginService, BoardRepository boardRepository){
        this.memberRepository = memberRepository;
        this.loginService = loginService;
        this.boardRepository = boardRepository;
    }

    @GetMapping("/address")
    public String addressGetTest(Model model){
        model.addAttribute("address", new Address());
        return "addressTest";
    }

    @GetMapping("/address/after")
    public String addresssPostAfter(@ModelAttribute("apiResponse")String apiResponse, @ModelAttribute("address") Address address, Model model){

        model.addAttribute("address", address);

        // JSON 응답을 파싱하여 jibunAddr 리스트 추출
        try {
            AddressResponse addressResponse = objectMapper.readValue(apiResponse, AddressResponse.class);
            model.addAttribute("errorMessage",addressResponse.getResults().getCommon().getErrorMessage());

            if (addressResponse.getResults().getJuso() != null){
                List<String> jibunAddrs = new ArrayList<>();

                for (AddressResponse.Results.Juso juso : addressResponse.getResults().getJuso()) {
                    jibunAddrs.add(juso.getJibunAddr());
                }

                model.addAttribute("jibunAddrs", jibunAddrs);
                log.info("jibunAddrs={}",jibunAddrs );
            }
        } catch (JsonProcessingException e) {
            model.addAttribute("error", "응답 처리 중 오류가 발생했습니다: " + e.getMessage());
        }


        return "addressAfterPost";
    }


    @PostMapping("address")
    public String addressPostTest(@ModelAttribute("address") Address address, RedirectAttributes redirectAttributes, Model model) throws URISyntaxException {


        String currentPage = String.valueOf(address.getCurrentPage()); // default : 1 (현재 페이지)
        String countPerPage = String.valueOf(address.getCountPerPage()); // default : 10 (한 페이지에 표현할 주소 개수)
        String resultType = address.getResultType(); // json
        String confmKey = "devU01TX0FVVEgyMDI0MTAxMjIxNDQzMDExNTE1MDk="; // key
        String keyword = address.getKeyword(); // address

        String apiUrl = "http://business.juso.go.kr/addrlink/addrLinkApi.do";
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("currentPage", currentPage)
                .queryParam("countPerPage", countPerPage)
                .queryParam("keyword", keyword)
                .queryParam("confmKey", confmKey)
                .queryParam("resultType", resultType)
                .build().toUri();
        log.info("uri ={}" ,uri);
        // API 호출
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            redirectAttributes.addFlashAttribute("apiResponse", response.getBody());
            log.info("apiResponse={}",response.getBody());
        } catch (Exception e) {
            model.addAttribute("error", "API 호출 중 오류가 발생했습니다: " + e.getMessage());
            log.info("apiResponse 오류 발생!!");
            return "errorPage";  // 오류 발생 시 에러 페이지로 이동
        }

        // 리다이렉트 시 전달할 데이터 설정
        redirectAttributes.addFlashAttribute("address", address);

        return "redirect:/members/address/after";
    }

    @Data
    static class Address{
        private String confmKey;
        private Integer currentPage;
        private Integer countPerPage;
        private String keyword;
        private String resultType;
    }

    @Data
    public static class AddressResponse{
        private Results results;

        @Data
        public static class Results{
            private Common common;
            private List<Juso> juso;

            @Data
            public static class Common{
                private String errorMessage;
                private String totalCount;
            }

            @Data
            public static class Juso{
                @JsonProperty("jibunAddr")
                private String jibunAddr;
            }
        }


    }

}
