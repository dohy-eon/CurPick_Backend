package com.curpick.CurPick.domain.worknet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "jobsList")
public class WorknetResponseDto {

    @JacksonXmlProperty(localName = "total")
    private int total;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "jobList")
    private List<Job> jobList;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
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