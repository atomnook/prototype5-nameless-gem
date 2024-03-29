package controllers.item

import domain.ops.Ops
import helpers.{AttributesControllerSpec, ChromeSpec, FirefoxSpec}
import play.api.mvc.Call
import protobuf.core.NamedAttributes

abstract class BoostControllerSpec extends AttributesControllerSpec {
  override protected[this] def list: Call = routes.BoostController.list()

  override protected[this] def get(a: NamedAttributes): Call = routes.BoostController.get(a.name.name)

  override protected[this] def ops: Ops[NamedAttributes] = service.boosts
}

class FirefoxBoostControllerSpec extends BoostControllerSpec with FirefoxSpec

class ChromeBoostControllerSpec extends BoostControllerSpec with ChromeSpec
