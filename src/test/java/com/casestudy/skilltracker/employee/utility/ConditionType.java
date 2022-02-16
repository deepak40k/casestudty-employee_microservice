package com.casestudy.skilltracker.employee.utility;

public enum ConditionType {

    CREATE_PROFILE("CREATE_PROFILE"),
    INVALID_ASSOCIATEID("INVALID_ASSOCIATEID"),
    INVALID_SKILL("INVALID_SKILL"),
    UPDATE_SKILL("UPDATE_SKILL"),
    UPDATE_SKILL_WITH_INVALID_SKILL("UPDATE_SKILL_WITH_INVALID_SKILL"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");
    private String requestFileName;
    private String responseFileName;
    private long httpStatus;

    private ConditionType(String result) {
        if("CREATE_PROFILE".equals(result)) {
            this.requestFileName = "SampleRequestSuccess.json";
            this.responseFileName = "SampleResponseSuccess.json";
            httpStatus=200;
        }
        if("INVALID_ASSOCIATEID".equals(result)) {
            this.requestFileName = "RequestInvalidAssociateId.json";
            this.responseFileName = "ResponseInvalidAssociateId.json";
            httpStatus=400;

        }
        if("INVALID_SKILL".equals(result)) {
            this.requestFileName = "RequestInvalidSkill.json";
            this.responseFileName = "UpdateSkillResponseInvalidSkill.json";
            httpStatus=400;
        }
        if("UPDATE_SKILL".equals(result)) {
            this.requestFileName = "UpdateSkillRequestSuccess.json";
            this.responseFileName = "UpdateSkillResponseSuccess.json";
            httpStatus=200;
        }
        if("UPDATE_SKILL_WITH_INVALID_SKILL".equals(result)) {
            this.requestFileName = "UpdateSkillRequestInvalidSkill.json";
            this.responseFileName = "UpdateSkillResponseInvalidSkill.json";
            httpStatus=400;
        }
        if("INTERNAL_SERVER_ERROR".equals(result)) {
            this.requestFileName = "SampleRequest500.json";
            this.responseFileName = "Response500.json";
            httpStatus=500;
        }
    }
    public String getRequestFileName() {
        return requestFileName;
    }
    public String getResponseFileName() {
        return responseFileName;
    }
    public long getHttpStatus() {
        return httpStatus;
    }

}