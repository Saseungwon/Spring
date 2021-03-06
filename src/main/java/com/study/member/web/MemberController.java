package com.study.member.web;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;
@Controller
public class MemberController {

	@Inject
	IMemberService memberService;
	@Inject
	ICommonCodeService codeService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute("jobList")
	public List<CodeVO> getJobList() {
		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
		return jobList;
	}

	@ModelAttribute("hobbyList")
	public List<CodeVO> getHobbyList() {
		List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
		return hobbyList;
	}

	@RequestMapping("/member/memberList.wow")
	public void memberList(@ModelAttribute("searchVO") MemberSearchVO searchVO, Model model) {
		logger.info("searchVO={}", searchVO);
		model.addAttribute("searchVO", searchVO);
		List<MemberVO> memberList = memberService.getMemberList(searchVO);
		model.addAttribute("memberList", memberList);
	}

	@RequestMapping("/member/memberView.wow")
	public String memberView(@RequestParam(value = "memId", required = true) String memId, Model model) {
		logger.info("memId={}", memId);
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setMessage("?????? ?????? ??? ??????");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("????????????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "member/memberView";
	}

	@RequestMapping("/member/memberEdit.wow")
	public String memberEdit(@RequestParam(value = "memId", required = true) String memId, Model model) {
		logger.info("memId={}", memId);
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setMessage("?????? ?????? ??? ??????");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "member/memberEdit";
	}

	@RequestMapping(value = "/member/meberModify.wow", params = "memid", method = RequestMethod.POST)
	public String memberModify(@ModelAttribute("member") MemberVO member, Model model) {
		logger.info("member={}", member);

		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.modifyMember(member);
			resultMessageVO.setMessage("?????? ??????");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberView.wow?member=" + member.getMemId());
			resultMessageVO.setUrlTitle("????????????");
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setMessage("?????? ?????? ??? ??????");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("???????????? ");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		return "common/message";
	}

	@RequestMapping(value = "/member/memberDelete", params = { "memid", "memPass" }, method = RequestMethod.POST)
	public String memberDelte(@ModelAttribute("member") MemberVO member, Model model) {
		logger.info("member={}", member);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.removeMember(member);
			resultMessageVO.setMessage("????????????");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setMessage("?????? ?????? ??? ??????");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("?????? ?????? ");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		return "common/message";
	}

	@RequestMapping(value = "/member/memberForm.wow")
	public void memberForm(Model model) {
	}

	@RequestMapping(value = "/member/memberRegist.wow", method = RequestMethod.POST)
	public ModelAndView memberRegist(@ModelAttribute("member") MemberVO member) {
		logger.info("member={}", member);

		ModelAndView mav = new ModelAndView();
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.registMember(member);
			resultMessageVO.setMessage("?????? ??????????????? ??????????????????");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		} catch (BizDuplicateKeyException bizDuplicateKeyException) {
			resultMessageVO.setMessage("???????????? ????????????");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("?????? ???");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("??????");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("????????????");
		}
		mav.addObject("resultMessageVO", resultMessageVO);
		mav.setViewName("common/message");
		return mav;
	}

}
