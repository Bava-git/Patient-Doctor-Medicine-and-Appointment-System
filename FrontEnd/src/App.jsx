import { BrowserRouter, Routes, Route } from "react-router-dom"
import { Toaster } from "sonner"

// Components
import PrivateComponents from "./PrivateComponents";
import Login from "./components/Login";
import LeftSliter from "./components/LeftSliter";
import Header from "./components/Header";
import { ListOfPatient, PatientModifer } from "./components/Patient";
import { ListOfDoctor, DoctorModifer } from "./components/Doctor";
import { ListOfAppointment, AppointmentModifer } from "./components/Appointment";
import { ListOfPatientAppointment, ListOfDoctorAppointment } from "./components/Patient_Appointment";
import { MedicineManager, MedicineManagerPatientSide } from "./components/Medicine";

// CSS
import "./App.css"
import "./assets/css/Login.css"
import "./assets/css/Appointment.css"
import "./assets/css/Patient.css"
import "./assets/css/Medicine.css"

function App() {

  return (
    <>
      <Toaster expand={true} richColors position='top-right' />
      <BrowserRouter>
        <LeftSliter />
        <Header />
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/*" element={<Page404 />} />
          <Route path="/patient/modifer" element={<PatientModifer />} />

          <Route element={<PrivateComponents allowedRoles={["ROLE_PATIENT", "ROLE_ADMIN"]} />}>
            <Route path="/appointment/list" element={<ListOfAppointment />} />
            <Route path="/patient/appointment" element={<ListOfPatientAppointment />} />
            <Route path="/patient/medicine" element={<MedicineManagerPatientSide />} />
          </Route>

          <Route element={<PrivateComponents allowedRoles={["ROLE_DOCTOR", "ROLE_ADMIN"]} />}>
            <Route path="/doctor/appointment" element={<ListOfDoctorAppointment />} />
            <Route path="/docotor/medicine/add/:patientappointmentID" element={<MedicineManager />} />
          </Route>

          <Route element={<PrivateComponents allowedRoles={["ROLE_ADMIN"]} />}>
            <Route path="/patient/list" element={<ListOfPatient />} />
            <Route path="/patient/modifer/:update_id" element={<PatientModifer />} />
            <Route path="/doctor/list" element={<ListOfDoctor />} />
            <Route path="/doctor/modifer" element={<DoctorModifer />} />
            <Route path="/doctor/modifer/:update_id" element={<DoctorModifer />} />
            <Route path="/appointment/modifer" element={<AppointmentModifer />} />
            <Route path="/appointment/modifer/:update_id" element={<AppointmentModifer />} />
          </Route>

        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App

const Page404 = () => {
  return (
    <>
      <h1>404 - Page not found</h1>
    </>
  )
}
