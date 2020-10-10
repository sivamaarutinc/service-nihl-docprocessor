package com.wsib.nihl.repository;

import com.wsib.nihl.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {
	public Claim findByReferenceNumberAndEmailAndStatus(String referenceNumber, String email, String status);

	public Optional<Claim> findByEmailEquals(String email);

	public List<Claim> findByStatusEquals(String status);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Claim c SET c.status = :status WHERE c.claimId = :claimId and c.status= :currentStatus")
	int updateStatusForFailed(@Param("claimId") int claimId, @Param("status") String status,
			@Param("currentStatus") String currentStatus);
}
