package nl.hu.greenify.core.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;

import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Intervention;
import nl.hu.greenify.core.domain.Person;
import nl.hu.greenify.core.domain.Phase;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.enums.FacilitatingFactor;
import nl.hu.greenify.core.domain.enums.Priority;
import nl.hu.greenify.core.domain.factor.Subfactor;

public class PhaseProgressDto {
    private Long id;
    private String name;
    private List<ParticipantDto> contenders;
    private List<CategoryDto> categories;

    public PhaseProgressDto(Long id, String name, List<ParticipantDto> contenders, List<CategoryDto> categories) {
        this.id = id;
        this.name = name;
        this.contenders = contenders;
        this.categories = categories;
    }

    public static PhaseProgressDto fromEntities(Intervention intervention, Phase phase, List<Person> participants) {
        if (phase.getSurveys().isEmpty()) 
            throw new IllegalArgumentException("Phase should have at least one survey");

        return new PhaseProgressDto(
            phase.getId(),
            phase.getName().getValue(),
            ParticipantDto.fromEntities(phase, participants),
            CategoryDto.fromEntities(phase.getSurveys().get(0))
        );
    }

    public static PhaseProgressDto fromEntity(Intervention intervention, Phase phase, Person participant) {
        if (phase.getSurveys().isEmpty()) 
            throw new IllegalArgumentException("Phase should have at least one survey");

        return new PhaseProgressDto(
            phase.getId(),
            phase.getName().getValue(),
            ParticipantDto.fromEntities(phase, List.of(participant)),
            CategoryDto.fromEntities(phase.getSurveys().get(0))
        );
    }

    public static class ParticipantDto {
        private Long id;
        private String name;
        private List<ProgressDto> progress;
        private List<ResponseDto> responses;
        
        public ParticipantDto(Long id, String name, List<ProgressDto> progress, List<ResponseDto> responses) {
            this.id = id;
            this.name = name;
            this.progress = progress;
            this.responses = responses;
        }

        public static List<ParticipantDto> fromEntities(Phase phase, List<Person> participants) {
            return participants.stream()
                .map(person -> fromEntity(phase, person))
                .collect(Collectors.toList());
        }

        public static ParticipantDto fromEntity(Phase phase, Person participant) {
            Survey survey = phase.getSurveyOfPerson(participant);
            return new ParticipantDto(
                participant.getId(), 
                participant.getFullName(), 
                survey.getCategories().stream()
                    .map(category -> ProgressDto.fromEntity(survey, category, participant))
                    .collect(Collectors.toList()),
                survey.getAllSubfactors().stream()
                    .filter(subfactor -> subfactor.getResponse() != null)
                    .map(subfactor -> ResponseDto.fromEntity(subfactor))
                    .collect(Collectors.toList())
            );
        }

        public static class ProgressDto {
            private Long categoryId;
            private double progress;

            public ProgressDto(Long categoryId, double progress) {
                this.categoryId = categoryId;
                this.progress = progress;
            }

            public static ProgressDto fromEntity(Survey survey, Category category, Person person) {
                return new ProgressDto(
                    category.getId(),
                    survey.getProgressByCategory(category)
                );
            }
        }

        public static class ResponseDto {
            private Long subfactorId;
            private boolean facilitatingFactor;
            private boolean priority;

            public ResponseDto(Long subfactorId, boolean facilitatingFactor, boolean priority) {
                this.subfactorId = subfactorId;
                this.facilitatingFactor = facilitatingFactor;
                this.priority = priority;
            }

            public static ResponseDto fromEntity(Subfactor subfactor) {
                boolean hasFacilitatingFactor = subfactor.getResponse().getFacilitatingFactor() != null && !subfactor.getResponse().getFacilitatingFactor().equals(FacilitatingFactor.PENDING);
                boolean hasPriority = subfactor.getResponse().getPriority() != null && !subfactor.getResponse().getPriority().equals(Priority.PENDING);
                return new ResponseDto(
                    subfactor.getId(),
                    hasFacilitatingFactor,
                    hasPriority
                );
            }
        }
    }

    public static class CategoryDto {
        private Long id;
        private String name;
        private List<SubfactorDto> subfactors;

        public CategoryDto(Long id, String name, List<SubfactorDto> subfactors) {
            this.id = id;
            this.name = name;
            this.subfactors = subfactors;
        }

        public static List<CategoryDto> fromEntities(Survey survey) {
            if (survey.getCategories().isEmpty()) 
                throw new IllegalArgumentException("Survey should have at least one category");

            return survey.getCategories().stream()
                .map(category -> new CategoryDto(
                    category.getId(),
                    category.getName(),
                    SubfactorDto.fromEntities(category.getSubfactors())
                ))
                .collect(Collectors.toList());
        }

        public static class SubfactorDto {
            private Long id;
            private int number;

            public SubfactorDto(Long id, int number) {
                this.id = id;
                this.number = number;
            }

            public static List<SubfactorDto> fromEntities(List<Subfactor> subfactors) {
                return subfactors.stream()
                    .map(subfactor -> new SubfactorDto(
                        subfactor.getId(),
                        subfactor.getNumber()
                    ))
                    .collect(Collectors.toList());
            }
        }
    }
}
