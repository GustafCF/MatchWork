package com.br.MatchWork.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.br.MatchWork.entity.enums.JobModel;
import com.br.MatchWork.entity.enums.TypeContract;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_JOBS")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String responsibility;
    private String Requirements;
    private String additionalInfo;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Set<ProcessSteps> steps = new HashSet<>();
    private JobModel jobModel;
    private TypeContract typeContract;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @OneToMany(mappedBy = "jobs")
    private Set<User> candidates = new HashSet<>();

    public Job() {}

    public Job(String name, String description, String responsibility, String requirements, String additionalInfo,
            JobModel jobModel, TypeContract typeContract, LocalDate date) {
        this.name = name;
        this.description = description;
        this.responsibility = responsibility;
        Requirements = requirements;
        this.additionalInfo = additionalInfo;
        this.jobModel = jobModel;
        this.typeContract = typeContract;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getRequirements() {
        return Requirements;
    }

    public void setRequirements(String requirements) {
        Requirements = requirements;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Set<ProcessSteps> getSteps() {
        return steps;
    }

    public JobModel getJobModel() {
        return jobModel;
    }

    public void setJobModel(JobModel jobModel) {
        this.jobModel = jobModel;
    }

    public TypeContract getTypeContract() {
        return typeContract;
    }

    public void setTypeContract(TypeContract typeContract) {
        this.typeContract = typeContract;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Job other = (Job) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}