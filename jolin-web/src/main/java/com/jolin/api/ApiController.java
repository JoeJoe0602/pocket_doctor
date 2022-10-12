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

@Api(tags = {"✅Api"})
@ApiSort(1)
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
    private ICasesService iCaseService;

    @Autowired
    private IPrescriptionService iPrescriptionService;


    @ApiOperation(value = "Search the articles on the home page")
    @PostMapping("searchArticle")
    public ResultDTO searchArticle(@RequestBody PageDTO<ArticleDTO> pageDTO) {
        PageDTO<ArticleDTO> articleDTOPageDTO = iArticleService.getPage(pageDTO);

        return new ResultDTO(articleDTOPageDTO);
    }




    @ApiOperation(value = " Find the list of doctor according to latitude and longitude")
    @PostMapping("findDoctor")
    public ResultDTO findDoctor(@RequestBody PageDTO<DoctorDTO> pageDTO) {
        PageDTO<DoctorDTO> doctorDTOPageDTO = iDoctorService.getPageDistance(pageDTO);

        return new ResultDTO(doctorDTOPageDTO);
    }


    @ApiOperation(value = "The detailed information of doctors")
    @GetMapping("getDoctorInfo")
    public ResultDTO getDoctorInfo(String id) {

        return new ResultDTO(iDoctorService.findById(id));
    }


    @ApiOperation(value = "Find clinic")
    @PostMapping("findClinic")
    public ResultDTO<PageDTO<ClinicDTO>> findClinic(@RequestBody  PageDTO<ClinicDTO> pageDTO) {
        System.out.println(pageDTO.getPage());
        PageDTO<ClinicDTO> clinicDTOPageDTO = iClinicService.getPageDistance(pageDTO);

        return new ResultDTO(clinicDTOPageDTO);
    }

    @ApiOperation(value = "The detailed information of clinics")
    @GetMapping("getClinicInfo")
    public ResultDTO getClinicInfo(String id) {

        return new ResultDTO(iClinicService.findById(id));
    }


    @ApiOperation(value = "Obtain the appointment record of user")
    @PostMapping("getAppointmentList")
    public ResultDTO getAppointmentList(@RequestBody PageDTO<AppointmentDTO> pageDTO) {
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

    @ApiOperation(value = "Make appointments")
    @PostMapping("appointment")
    public ResultDTO appointment(@RequestBody AppointmentDTO appointmentDTO) {
        return new ResultDTO(iAppointmentService.create(appointmentDTO));
    }

    @ApiOperation(value = "Obtain the category of the articles")
    @PostMapping("getCategory")
    public ResultDTO getCategory(@RequestBody PageDTO<CategoryDTO> pageDTO) {
        PageDTO<CategoryDTO> categoryDTOPageDTO = iCategoryService.getPage(pageDTO);

        return new ResultDTO(categoryDTOPageDTO);
    }


    @ApiOperation(value = "Obtain the list of the articles")
    @PostMapping("getArticleList")
    public ResultDTO getArticleList(@RequestBody PageDTO<ArticleDTO> pageDTO) {
        PageDTO<ArticleDTO> articleDTOPageDTO = iArticleService.getPage(pageDTO);

        return new ResultDTO(articleDTOPageDTO);
    }


    @ApiOperation(value = "Obtain the content of the articles")
    @PostMapping("getArticleContent")
    public ResultDTO getArticleContent(String id) {

        return new ResultDTO(iArticleService.findById(id));
    }


    @ApiOperation(value = "Obtain the list of the carousel")
    @PostMapping("getCarouselList")
    public ResultDTO getCarouselList(@RequestBody PageDTO<CarouselDTO>  pageDTO) {
        PageDTO<CarouselDTO> carouselDTOPageDTO = iCarouselService.getPage(pageDTO);

        return new ResultDTO(carouselDTOPageDTO);
    }

    @ApiOperation(value = "Obtain the notifications")
    @GetMapping("getNotification")
    public ResultDTO getNotification(String userId) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        return new ResultDTO(iNotificationService.getNotification(notification));
    }

    @ApiOperation(value = "Add the single chat record")
    @PostMapping("addSingleChatRecord")
    public ResultDTO addSingleChatRecord(@RequestBody ChatRecordDTO chatRecordDTO) {
        return new ResultDTO(iChatRecordService.create(chatRecordDTO));
    }

    @ApiOperation(value = "Obtain the chat record")
    @PostMapping("getChatRecord")
    public ResultDTO getChatRecord(String userId, String doctorId) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setUserId(userId);
        chatRecord.setDoctorId(doctorId);
        return new ResultDTO(iChatRecordService.getChatRecord(chatRecord));
    }

    @ApiOperation(value = "Obtain the list of the chat record")
    @PostMapping("getChatRecordList")
    public ResultDTO getChatRecordList(String userId) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setUserId(userId);
        return new ResultDTO(iChatRecordService.getChatRecordList(chatRecord));
    }

    @ApiOperation(value = "Add collect")
    @PostMapping("addCollect")
    public ResultDTO addCollect(@RequestBody CollectDTO collectDTO) {
        return new ResultDTO(iCollectService.create(collectDTO));
    }

    @ApiOperation(value = "Obtain the list of the collect")
    @PostMapping("getCollectList")
    public ResultDTO getCollectList(@RequestBody PageDTO<CollectDTO>  pageDTO) {
        PageDTO<CollectDTO> collectDTOPageDTO = iCollectService.getPage(pageDTO);
        return new ResultDTO(collectDTOPageDTO);
    }

    @ApiOperation(value = "Obtain the list of the case")
    @PostMapping("getCasesList")
    public ResultDTO getCasesList(@RequestBody PageDTO<CasesDTO> pageDTO) {

        PageDTO<CasesDTO> casesDTOPageDTO = iCaseService.getPage(pageDTO);

        return new ResultDTO(casesDTOPageDTO);
    }

    @ApiOperation(value = "Obtain the list of the prescription")
    @PostMapping("getPrescriptionList")
    public ResultDTO getPrescriptionList(@RequestBody PageDTO<PrescriptionDTO>  pageDTO) {

        PageDTO<PrescriptionDTO> prescriptionDTOPageDTO = iPrescriptionService.getPage(pageDTO);

        return new ResultDTO(prescriptionDTOPageDTO);

    }

    @ApiOperation(value = "Obtain the list of the health record of user")
    @PostMapping("getHealthRecord")
    public ResultDTO getHealthRecordList(String userId) {
        HealthRecord healthRecord=new HealthRecord();
        healthRecord.setUserId(userId);
        return new ResultDTO(iHealthRecordService.getHealthRecord(healthRecord));
    }

}
