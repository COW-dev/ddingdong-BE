package ddingdong.ddingdongBE.domain.formapplication.service.dto.query;

import ddingdong.ddingdongBE.domain.form.entity.FieldType;

import ddingdong.ddingdongBE.domain.formapplication.entity.FormAnswer;
import ddingdong.ddingdongBE.domain.formapplication.entity.FormApplication;
import ddingdong.ddingdongBE.domain.formapplication.entity.FormApplicationStatus;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FormApplicationQuery(
    LocalDateTime createdAt,
    String name,
    String studentNumber,
    String department,
    FormApplicationStatus status,
    List<FormFieldAnswerListQuery> formFieldAnswers
) {

  @Builder
  public record FormFieldAnswerListQuery(
      Long fieldId,
      String question,
      FieldType type,
      List<String> options,
      Boolean required,
      Integer order,
      String section,
      List<String> value
  ) {

    public static FormFieldAnswerListQuery from(FormAnswer formAnswer) {
      return FormFieldAnswerListQuery.builder()
          .fieldId(formAnswer.getFormField().getId())
          .question(formAnswer.getFormField().getQuestion())
          .type(formAnswer.getFormField().getFieldType())
          .options(formAnswer.getFormField().getOptions())
          .required(formAnswer.getFormField().isRequired())
          .order(formAnswer.getFormField().getFieldOrder())
          .section(formAnswer.getFormField().getSection())
          .value(formAnswer.getValue())
          .build();
    }
  }

  public static FormApplicationQuery of(FormApplication formApplication,
      List<FormAnswer> formAnswers) {
    List<FormFieldAnswerListQuery> formFieldAnswerListQueries = formAnswers.stream()
        .map(FormFieldAnswerListQuery::from)
        .toList();
    return FormApplicationQuery.builder()
        .createdAt(formApplication.getCreatedAt())
        .name(formApplication.getName())
        .studentNumber(formApplication.getStudentNumber())
        .department(formApplication.getDepartment())
        .status(formApplication.getStatus())
        .formFieldAnswers(formFieldAnswerListQueries)
        .build();
  }
}
