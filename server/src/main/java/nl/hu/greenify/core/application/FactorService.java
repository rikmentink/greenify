package nl.hu.greenify.core.application;

import nl.hu.greenify.core.data.FactorRepository;
import nl.hu.greenify.core.domain.factor.Factor;

public class FactorService {
    private final FactorRepository factorRepository;

    public FactorService(FactorRepository factorRepository) {
        this.factorRepository = factorRepository;
    }

    public Factor getFactorById(Long id) {
        return factorRepository.findById(id).orElse(null);
    }
}
