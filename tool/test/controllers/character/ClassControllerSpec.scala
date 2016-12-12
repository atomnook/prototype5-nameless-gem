package controllers.character

import domain.ops.Ops
import helpers.{AttributesControllerSpec, ChromeSpec, FirefoxSpec}
import play.api.mvc.Call
import protobuf.core.NamedAttributes

abstract class ClassControllerSpec extends AttributesControllerSpec {
  override protected[this] def list: Call = routes.ClassController.list()

  override protected[this] def get(a: NamedAttributes): Call = routes.ClassController.get(a.name.name)

  override protected[this] def ops: Ops[NamedAttributes] = service.classes
}

class FirefoxClassControllerSpec extends ClassControllerSpec with FirefoxSpec

class ChromeClassControllerSpec extends ClassControllerSpec with ChromeSpec
