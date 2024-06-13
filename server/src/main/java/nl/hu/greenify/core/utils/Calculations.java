package nl.hu.greenify.core.utils;

import nl.hu.greenify.core.domain.Category;
import nl.hu.greenify.core.domain.Response;
import nl.hu.greenify.core.domain.Survey;
import nl.hu.greenify.core.domain.factor.Factor;
import nl.hu.greenify.core.domain.factor.Subfactor;

import java.util.List;
import java.util.Objects;

public class Calculations {
    public static double calculatePersonalProgress(List<Survey> surveys) {
        List<Factor> factors = surveys.stream()
                .map(Survey::getCategories)
                .flatMap(List::stream)
                .map(Category::getFactors)
                .flatMap(List::stream)
                .toList();

        List<Subfactor> subfactors = factors.stream()
                .map(Factor::getSubfactors)
                .flatMap(List::stream)
                .toList();

        List<Response> responses = subfactors.stream()
                .map(Subfactor::getResponse)
                .filter(Objects::nonNull)
                .toList();

        return ((double) responses.size() / subfactors.size()) * 100;
    }

    public static double calculatePhaseProgress() {
        return 0;
    }
}
