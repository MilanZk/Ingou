package com.mkokic.domain.ingou

class GetAllIngous (private val ingouRepository: IngouRepository) {
    operator fun invoke() = ingouRepository.getAllIngous()
}
