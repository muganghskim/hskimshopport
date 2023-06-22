package com.hsproject.envmarket.oauth

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(name = "name")
        @Enumerated(EnumType.STRING)
        val name: RoleName = RoleName.NORMAL,
)
enum class RoleName {
    ADMIN,
    MEMBER,
    NORMAL
}
