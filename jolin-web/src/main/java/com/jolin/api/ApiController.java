package com.jolin.api;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jolin.common.dto.PageDTO;
import com.jolin.common.dto.ResultDTO;
import com.jolin.domain.ChatRecord;
import com.jolin.domain.HealthRecord;
import com.jolin.domain.Notification;
import com.jolin.dto.*;
import com.jolin.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private INotificationService iNotificationService;

    @Autowired
    private IChatRecordService iChatRecordService;

    @Autowired
    private ICollectService iCollectService;

    @Autowired
    private  IHealthRecordService iHealthRecordService;

    @Autowired
    private  ICaseService iCaseService;

    @Autowired
    private IPrescriptionService iPrescriptionService;


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

    @ApiOperation(value = "预约")
    @PostMapping("appointment")
    public ResultDTO appointment(@RequestBody AppointmentDTO appointmentDTO) {
        return new ResultDTO(iAppointmentService.create(appointmentDTO));
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

    @ApiOperation(value = "获取通知")
    @GetMapping("getNotification")
    public ResultDTO getNotification(String userId) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        return new ResultDTO(iNotificationService.getNotification(notification));
    }

    @ApiOperation(value = "新增聊天记录")
    @PostMapping("addSingleChatRecord")
    public ResultDTO addSingleChatRecord(@RequestBody ChatRecordDTO chatRecordDTO) {
        return new ResultDTO(iChatRecordService.create(chatRecordDTO));
    }

    @ApiOperation(value = "获取聊天记录")
    @PostMapping("getChatRecord")
    public ResultDTO getChatRecord(String userId, String doctorId) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setUserId(userId);
        chatRecord.setDoctorId(doctorId);
        return new ResultDTO(iChatRecordService.getChatRecord(chatRecord));
    }

    @ApiOperation(value = "获取聊天记录列表")
    @PostMapping("getChatRecordList")
    public ResultDTO getChatRecordList(String userId) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setUserId(userId);
        return new ResultDTO(iChatRecordService.getChatRecordList(chatRecord));
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("addCollect")
    public ResultDTO addCollect(@RequestBody CollectDTO collectDTO) {
        return new ResultDTO(iCollectService.create(collectDTO));
    }

    @ApiOperation(value = "获取收藏列表")
    @PostMapping("getCollectList")
    public ResultDTO getCollectList(PageDTO pageDTO) {
        PageDTO<CollectDTO> collectDTOPageDTO = iCollectService.getPage(pageDTO);
        return new ResultDTO(collectDTOPageDTO);
    }

    @ApiOperation(value = "获取病例列表")
    @PostMapping("getCaseList")
    public ResultDTO getCaseList(PageDTO pageDTO) {

        PageDTO<CaseDTO> caseDTOPageDTO = iCaseService.getPage(pageDTO);

        return new ResultDTO(caseDTOPageDTO);
    }

    @ApiOperation(value = "获取处方列表")
    @PostMapping("getPrescriptionList")
    public ResultDTO getPrescriptionList(PageDTO pageDTO) {

        PageDTO<PrescriptionDTO> prescriptionDTOPageDTO = iPrescriptionService.getPage(pageDTO);

        return new ResultDTO(prescriptionDTOPageDTO);

    }
}
