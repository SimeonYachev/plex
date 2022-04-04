package com.scalefocus.java.simeonyachev.repositories.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.StreamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamTypeRepository extends JpaRepository<StreamType, Integer> {
}
