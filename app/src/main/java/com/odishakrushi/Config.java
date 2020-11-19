package com.odishakrushi;

/**
 * Created by admin on 20-03-2017.
 */
public class Config
{

    public static String baseUrl="http://odishakrushi.in/index.php/api/";
  //  public static String baseUrl="http://demo.ratnatechnology.co.in/api/";
    public static String signup=baseUrl+"api/user/signup";
    public static String forgot_password=baseUrl+"api/user/forgot_password";

    public static String farmersignupcommon=baseUrl+"user/signup_common";
    public static String farmersignup=baseUrl+"user/signup_farmer";
    public static String farmerapplogin=baseUrl+"user/login";
    public static String business_signup=baseUrl+"user/signup_businessMan";
    public static String adminmanager_signup=baseUrl+"user/signup_admin";
    public static String resourceperson_signup=baseUrl+"user/signup_resource_person";
    public static String extoff_signup=baseUrl+"user/signup_extension_officer";
    public static String profile_image_upload=baseUrl+"user/image_upload";
    public static String sturesignup=baseUrl+"user/signup_StudentResearcher";

    public static String apiaqna=baseUrl+"qna/setFarmerQna";
    public static String apimystory=baseUrl+"qna/setmystory";

    public static String profilepicupload=baseUrl+"upload/profile_img_upload";
    public static String getUserCount=baseUrl+"user/getUsersCount";
    public static String giveAnswertoFarmer=baseUrl+"qna/set_answer";
    public static String farmersales=baseUrl+"sales/sales";
    public static String getallsalesbyuserId=baseUrl+"sales/getallsalesbyuserId";// changed 'farmer' to 'sales'
    public static String del_allsalesbyuserId= baseUrl+"farmer/del_allsalesbyuserId";
    public static String getAd= baseUrl+"advertise/getadvertise";
    public static String surveyQuestion= baseUrl+"survey/surveyQuestion";
    public static String getReqQuestion= baseUrl+"survey/getReqQuestion";
    public static String getSurveyQuestion= baseUrl+"survey/getquestion";
    public static String surveyAnswer= baseUrl+"survey/surveyAnswer";
    public static String getProfileData= baseUrl+"user/getFarmer";
    public static String getStudentProfileData= baseUrl+"user/getStudentResearcher";
    public static String getBusinessManProfileData= baseUrl+"user/getBusinessMan";
    public static String getAdminManagerProfileData= baseUrl+"user/getAdmin";
    public static String getExtensionOfficerProfileData= baseUrl+"user/getExtensionOfficer";
    public static String getGuestProfileData= baseUrl+"user/getGuest";
    public static String getMachines=baseUrl+"user/getConfig?config_key=MACHINE_TOOL";
    public static String getTypeOfControl=baseUrl+"user/getpost1_typs_of_control";
    public static String getServiceStatus=baseUrl+"user/getpost2_service_status";
    public static String getDepartment=baseUrl+"user/getpost3_department?post1_id=";
    public static String getDcar=baseUrl+"user/getpost4_dcarci?post3_id=";
    public static String getPost=baseUrl+"user/getpost5_post?";
    public static String getJurisdiction=baseUrl+"user/getpost6_jurisdiction?post5_id=";
    public static String changePassword=baseUrl+"user/change_password";

    //CONSTANTS
    public static  String ODIA_DISTRICTS="\n" +
            "{\n" +
            "status: true,\n" +
            "districts: \n" +
            "[\n" +
            "{\n" +
            "id: \"1\",\n" +
            "name: \"ଅନୁଗୁଳ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"2\",\n" +
            "name: \"ବଲାଙ୍ଗୀର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"3\",\n" +
            "name: \"ବାଲେଶ୍ବର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"4\",\n" +
            "name: \"ବରଗଡ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"5\",\n" +
            "name: \"ବୌଧ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"6\",\n" +
            "name: \"ଭଦ୍ରକ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"7\",\n" +
            "name: \"କଟକ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"8\",\n" +
            "name: \"ଦେବଗଡ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"9\",\n" +
            "name: \"ଢେଙ୍କାନାଳ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"10\",\n" +
            "name: \"ଗଜପତି\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"11\",\n" +
            "name: \"ଗଂଜାମ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"12\",\n" +
            "name: \"ଜଗତସିଂହପୁର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"13\",\n" +
            "name: \"ଯାଜପୁର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"14\",\n" +
            "name: \"ଝାରସୁଗୁଡା\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"15\",\n" +
            "name: \"କଳାହାଣ୍ଡି\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"16\",\n" +
            "name: \"କନ୍ଧମାଳ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"17\",\n" +
            "name: \"କେନ୍ଦ୍ରାପଡ଼ା\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"18\",\n" +
            "name: \"କେନ୍ଦୁଝର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"19\",\n" +
            "name: \"ଖୋର୍ଦ୍ଧା\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"20\",\n" +
            "name: \"କୋରାପୁଟ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"21\",\n" +
            "name: \"ମାଲକାନଗିରି\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"22\",\n" +
            "name: \"ମୟୁରଭଞ୍ଜ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"23\",\n" +
            "name: \"ନବରଙ୍ଗପୁର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"24\",\n" +
            "name: \"ନୟାଗଡ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"25\",\n" +
            "name: \"ନୂଆପଡା\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"26\",\n" +
            "name: \"ପୁରୀ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"1\"\n" +
            "},\n" +
            "{\n" +
            "id: \"27\",\n" +
            "name: \"ରାୟଗଡା\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"3\"\n" +
            "},\n" +
            "{\n" +
            "id: \"28\",\n" +
            "name: \"ସମ୍ବଲପୁର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"29\",\n" +
            "name: \"ସୋନପୁର\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "},\n" +
            "{\n" +
            "id: \"30\",\n" +
            "name: \"ସୁନ୍ଦରଗଡ\",\n" +
            "state_id: \"29\",\n" +
            "zone: \"2\"\n" +
            "}\n" +
            "]\n" +
            "}";
}
