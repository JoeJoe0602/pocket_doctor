package com.jolin.api;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.dto.ResultDTO;
import com.jolin.dto.*;
import com.jolin.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"2.角色接口"})
@ApiSort(2)
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private IDoctorService iDoctorService;
    @Autowired
    private IAppointmentService iAppointmentService;
    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private ICarouselService iCarouselService;

    @Autowired
    private IClinicService iClinicService;


    @ApiOperation(value = "在首页搜索文章")
    @PostMapping("searchArticle")
    public ResultDTO searchArticle(PageDTO pageDTO) {
        PageDTO<ArticleDTO> articleDTOPageDTO = iArticleService.getPage(pageDTO);

        return new ResultDTO(articleDTOPageDTO);
    }


    @ApiOperation(value = " 根据经纬度，查询医生列表")
    @PostMapping("findDoctor")
    public ResultDTO findDoctor(PageDTO pageDTO) {
        PageDTO<DoctorDTO> doctorDTOPageDTO = iDoctorService.getPageDistance(pageDTO);

        return new ResultDTO(doctorDTOPageDTO);
    }


    @ApiOperation(value = "医生详情")
    @GetMapping("getDoctorInfo")
    public ResultDTO getDoctorInfo(String id) {

        return new ResultDTO(iDoctorService.findById(id));
    }


    @ApiOperation(value = "查询诊所")
    @PostMapping("findClinic")
    public ResultDTO findClinic(PageDTO pageDTO) {
        PageDTO<ClinicDTO> clinicDTOPageDTO = iClinicService.getPageDistance(pageDTO);

        return new ResultDTO(clinicDTOPageDTO);
    }

    @ApiOperation(value = "诊所详情")
    @GetMapping("getClinicInfo")
    public ResultDTO getClinicInfo(String id) {

        return new ResultDTO(iClinicService.findById(id));
    }


    @ApiOperation(value = "获取用户的预约记录")
    @PostMapping("getAppointmentList")
    public ResultDTO getAppointmentList(PageDTO pageDTO) {
        PageDTO<AppointmentDTO> appointmentDTOPageDTO = iAppointmentService.getPage(pageDTO);
        List appointmentList = appointmentDTOPageDTO.getList();
        for (int i = 0; i < appointmentList.size(); i++) {
            AppointmentDTO appointmentDTO = (AppointmentDTO) appointmentList.get(i);
            UserInfoDTO user = (UserInfoDTO) iUserInfoService.findById(appointmentDTO.getUserId());
            appointmentDTO.setUser(user);
            UserInfoDTO doctor = (UserInfoDTO) iDoctorService.findById(appointmentDTO.getDoctorId());
            appointmentDTO.setDoctor(doctor);


        }
        return new ResultDTO(appointmentDTOPageDTO);
    }


    @ApiOperation(value = "获取文章分类")
    @PostMapping("getCategory")
    public ResultDTO getCategory(PageDTO pageDTO) {
        PageDTO<CategoryDTO> categoryDTOPageDTO = iCategoryService.getPage(pageDTO);

        return new ResultDTO(categoryDTOPageDTO);
    }


    @ApiOperation(value = "获取文章列表")
    @PostMapping("getArticleList")
    public ResultDTO getArticleList(PageDTO pageDTO) {
        PageDTO<ArticleDTO> articleDTOPageDTO = iArticleService.getPage(pageDTO);

        return new ResultDTO(articleDTOPageDTO);
    }


    @ApiOperation(value = "获取文章内容")
    @PostMapping("getArticleContent")
    public ResultDTO getArticleContent(String id) {

        return new ResultDTO(iArticleService.findById(id));
    }


    @ApiOperation(value = "获取轮播图列表")
    @PostMapping("getCarouselList")
    public ResultDTO getCarouselList(PageDTO pageDTO) {
        PageDTO<CarouselDTO> carouselDTOPageDTO = iCarouselService.getPage(pageDTO);

        return new ResultDTO(carouselDTOPageDTO);
    }


}
