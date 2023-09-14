package org.tagwonder.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.tagwonder.models.TagDataModel

interface TagJpaRepository: JpaRepository<TagDataModel, Long>