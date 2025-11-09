package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.constant.RankCountProjection;
import com.example.webbanhang.entity.Xephanguser;

@Repository
public interface XephanguserRepository extends JpaRepository<Xephanguser, Long> {
    List<Xephanguser> findAll();

    @Query(
            value =
                    """
		SELECT x.id AS xephanguserId,
			x.hangmuc AS hangmuc,
			COUNT(u.id) AS userCount
		FROM xephanguser x
		LEFT JOIN `user` u ON u.tongtien >= x.giatien
		LEFT JOIN xephanguser x2 ON x2.giatien > x.giatien AND u.tongtien >= x2.giatien
		WHERE x2.id IS NULL
		GROUP BY x.id, x.hangmuc
		ORDER BY x.giatien ASC
		""",
            nativeQuery = true)
    List<RankCountProjection> countUsersPerRank();
}
