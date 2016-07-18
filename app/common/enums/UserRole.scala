package common.enums

import common.enums.base.{EnumHolder, EnumLike}

/**
 * @author iRevThis
 */
trait UserRole extends EnumLike

object UserRole extends EnumHolder[UserRole] {
  override def values: Seq[UserRole] = Seq(Admin, User)

  case object Admin extends UserRole {
    override def name: String = "Admin"
    override def code: Int = 0
  }

  case object User extends UserRole {
    override def name: String = "User"
    override def code: Int = 1
  }
}
