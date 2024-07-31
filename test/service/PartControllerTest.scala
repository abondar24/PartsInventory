package service

import akka.util.Timeout
import controller.PartController
import exception.{PartDetailNotFoundException, PartExceptionHandler, PartNotFoundException}
import model.web.{PartDetailResponse, PartResponse}
import model.{Part, PartDetail}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{doNothing, reset, verify, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.*
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.http.Status.{BAD_REQUEST, CREATED, NOT_FOUND, NO_CONTENT, OK}
import play.api.libs.json.Json
import play.api.test.Helpers.{DELETE, GET, POST, contentAsJson, contentType, defaultAwaitTimeout, status, stubControllerComponents}
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.*

class PartControllerTest extends PlaySpec with MockitoSugar with BeforeAndAfterEach{

  implicit val ec: ExecutionContext = ExecutionContext.global
  implicit val timeout: Timeout = Timeout(5.seconds)

  private val controllerComponents = stubControllerComponents()

  private val partService = mock[PartService]

  private val partController = new PartController(controllerComponents, partService)(ec)

  private val partExceptionHandler = new PartExceptionHandler()(ec)


  // Reset mocks before each test
  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(partService)
  }

  "PartController" should {

    "create part with details successfully" in {
      // Define the request body as JSON
      val partCreateRequest = Json.obj(
        "name" -> "Part A",
        "quantity" -> 10,
        "price" -> 99.99,
        "details" -> Json.arr(Json.obj("description" -> "Detail A"))
      )


      doNothing().when(partService).create(any[Part], any[Seq[PartDetail]])

      // Create a FakeRequest with POST method
      val request = FakeRequest(POST, "/parts")
        .withBody(partCreateRequest)
        .withHeaders("Content-Type" -> "application/json")

      // Apply the request to the controller and get the result
      val result = partController.create().apply(request)

      // Await the result and check the status and content type
      status(result) shouldBe CREATED
      contentType(result) shouldBe Some("application/json")
    }

    "should noy create part with invalid body" in {
      val partCreateRequest = Json.obj(
        "name" -> "Part A",
        "quantity" -> 10,
        "details" -> Json.arr(Json.obj("description" -> "Detail A"))
      )

      val errorJson = Json.obj(
        "error" -> Json.obj(
          "obj.price" -> Json.arr(
            Json.obj(
              "msg" -> Json.arr("error.path.missing"),
              "args" -> Json.arr()
            )
          )
        )
      )

      val request = FakeRequest(POST, "/parts")
        .withBody(partCreateRequest)
        .withHeaders("Content-Type" -> "application/json")

      // Apply the request to the controller and get the result
      val result = partController.create().apply(request)

      status(result) shouldBe BAD_REQUEST
      contentAsJson(result) shouldBe errorJson
    }
    
    "should find part by id" in {
      val part = Part(id = Some(1L), name = "Part A", quantity = 10, price = 99.99)
      val partResponse = PartResponse(name = part.name, quantity = part.quantity, price = part.price)
      
      when(partService.findById(1L)).thenReturn(part)

      val request = FakeRequest(GET, "/parts/1")
        .withHeaders("Content-Type" -> "application/json")

      val result = partController.findById(1L).apply(request)

      status(result) shouldBe OK
      contentType(result) shouldBe Some("application/json")
      contentAsJson(result) shouldBe Json.toJson(partResponse)

    }

    "should return not found on part not found exception" in {

      val request = FakeRequest(GET, "/parts/1")
        .withHeaders("Content-Type" -> "application/json")

      val result = partExceptionHandler.onServerError(request,PartNotFoundException())

      status(result) shouldBe NOT_FOUND
      contentType(result) shouldBe Some("application/json")
      contentAsJson(result) shouldBe Json.obj("error" -> "Part not found")
    }

    "should find part by name" in {
      val part = Part(id = Some(1L), name = "Part A", quantity = 10, price = 99.99)
      val partResponse = PartResponse(name = part.name, quantity = part.quantity, price = part.price)

      when(partService.findByName(part.name)).thenReturn(part)

      val request = FakeRequest(GET, "/parts/name/"+part.name)
        .withHeaders("Content-Type" -> "application/json")

      val result = partController.findByName(part.name).apply(request)

      status(result) shouldBe OK
      contentType(result) shouldBe Some("application/json")
      contentAsJson(result) shouldBe Json.toJson(partResponse)

    }


    "should find all parts " in {
      val part = Part(id = Some(1L), name = "Part A", quantity = 10, price = 99.99)
      val partResponse = PartResponse(name = part.name, quantity = part.quantity, price = part.price)

      when(partService.findAll(0,1)).thenReturn(Seq(part))

      val request = FakeRequest(GET, "/parts?offset=0&limit=1")
        .withHeaders("Content-Type" -> "application/json")

      val result = partController.findAll().apply(request)

      status(result) shouldBe OK
      contentType(result) shouldBe Some("application/json")
      contentAsJson(result) shouldBe Json.toJson(List(partResponse))

    }


    "should not find parts " in {
      when(partService.findAll(0, 1)).thenReturn(Seq())

      val request = FakeRequest(GET, "/parts?offset=0&limit=1")
        .withHeaders("Content-Type" -> "application/json")

      val result = partController.findAll().apply(request)

      status(result) shouldBe NO_CONTENT

    }

    "should return bad request when finds parts with no offset and limit " in {
      val request = FakeRequest(GET, "/parts")
        .withHeaders("Content-Type" -> "application/json")

      val result = partController.findAll().apply(request)

      status(result) shouldBe BAD_REQUEST
      contentType(result) shouldBe Some("application/json")
      contentAsJson(result) shouldBe Json.obj("error"->"Invalid or missing query parameters")
    }


    "should find part details " in {
      val partDetail = PartDetail(id = Some(1L), partId = Some(1L), description = "test")
      val detailResponse = PartDetailResponse(description = partDetail.description)

      when(partService.findPartDetails(partDetail.id.get,0,1)).thenReturn(Seq(partDetail))

      val request = FakeRequest(GET, "/parts/1/details?offset=0&limit=1")
        .withHeaders("Content-Type" -> "application/json")

      val result = partController.findPartDetails(partDetail.partId.get).apply(request)

      status(result) shouldBe OK
      contentType(result) shouldBe Some("application/json")
      contentAsJson(result) shouldBe Json.toJson(List(detailResponse))

    }

    "should return not found on part detail not found exception" in {

      val request = FakeRequest(GET, "/parts/1/details")
        .withHeaders("Content-Type" -> "application/json")

      val result = partExceptionHandler.onServerError(request, PartDetailNotFoundException())

      status(result) shouldBe NOT_FOUND
      contentType(result) shouldBe Some("application/json")
      contentAsJson(result) shouldBe Json.obj("error" -> "Part detail not found")
    }

    //TODO: add update tests


    "should delete part" in {

      doNothing().when(partService).delete(1L)

      val request = FakeRequest(DELETE, "/parts/1")
        .withHeaders("Content-Type" -> "application/json")

      val result = partController.delete(1L).apply(request)

      status(result) shouldBe OK
      contentType(result) shouldBe Some("application/json")
      verify(partService).delete(1L)

    }
  }
}
