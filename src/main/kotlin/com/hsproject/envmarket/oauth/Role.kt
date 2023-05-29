package com.hsproject.envmarket.oauth

import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(name = "name")
        @Enumerated(EnumType.STRING)
        val name: RoleName = RoleName.NORMAL,

        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
        val users: MutableSet<User> = HashSet()
)
enum class RoleName {
    ADMIN,
    MEMBER,
    NORMAL
}
