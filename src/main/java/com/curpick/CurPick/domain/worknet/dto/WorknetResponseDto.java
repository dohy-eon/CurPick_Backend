package com.curpick.CurPick.domain.worknet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorknetResponseDto {
    @JacksonXmlProperty(localName = "total")
    private int total;

    @JacksonXmlProperty(localName = "jobList") // jobList 안에 단일 job 객체가 있어서
    private Job jobList;

    @Getter @Setter
    public static class Job {
        @JacksonXmlProperty(localName = "jobClcd")
        private String jobClcd;

        @JacksonXmlProperty(localName = "jobClcdNM")
        private String jobClcdNM;

        @JacksonXmlProperty(localName = "jobCd")
        private String jobCd;

        @JacksonXmlProperty(localName = "jobNm")
        private String jobNm;
    }
}