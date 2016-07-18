package model.prototypes

import common.utils.geometry.Location
import model.L2Object

/**
 * @author iRevThis
 */
case class L2Drop(objectId: Int, location: Location, my: Boolean, count: Int, stackable: Boolean) extends L2Object
