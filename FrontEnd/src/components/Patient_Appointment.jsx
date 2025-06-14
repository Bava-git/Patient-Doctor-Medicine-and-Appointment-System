import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { toast } from "sonner";
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const ListOfPatientAppointment = () => {
    const [PatientAppointmentList, setPatientAppointmentList] = useState([]);
    let UserID = jwtDecode(localStorage.getItem("token")).userId;

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            let response = await axios.get("http://localhost:3000/patientappointments", {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            let data = response.data;
            let Filterdata = data.filter((element) => {
                return element.patient_id === UserID
            })
            setPatientAppointmentList(Filterdata)

        } catch (error) {
            console.log("List Of Doctor " + error);
        }
    }

    const handleDelete = async (id) => {

        try {
            let response = await axios.delete(`http://localhost:3000/patientappointments/delete/${id}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (response.status === 200) {
                toast.success("Deleted successfully!")
                fetchData();
            }

        } catch (error) {
            console.log("List Of Doctor " + error);
        }

    }

    const MyArr = [];
    let length = PatientAppointmentList?.length ?? 0;
    for (let i = 0; i < length; i++) {
        const element = PatientAppointmentList[i];
        MyArr.push(<tr key={i}>
            <td>{i + 1}</td>
            <td>{element.doctor_name + " " + element.doctor_education}</td>
            <td>{element.doctor_specializedfield}</td>
            <td>{format(element.appointment_datetime, "dd-MM-yyyy")}</td>
            <td>{format(element.appointment_datetime, "hh:mm a")}</td>
            <td><button className="listofpatient-table-bookBn" onClick={() => { handleDelete(element.patientappointment_id) }}>Delete</button></td>
        </tr>)
    }

    return (
        <div className="listofpatient-Container">
            <div className="listofpatient-table">
                <h3 className="listofpatient-table-title">Patient appointment log</h3>
                <table border={1}>
                    <thead>
                        <tr>
                            <th>S No</th>
                            <th>Doctor Name</th>
                            <th>Doctor Specialize</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {MyArr.length > 0 ? MyArr :
                            <tr key="temp">
                                <td colSpan={6} className="medicine-error-massage">No Data</td>
                            </tr>}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

const ListOfDoctorAppointment = () => {
    const [DoctorAppointmentList, setDoctorAppointmentList] = useState([]);
    const Navigate = useNavigate();
    let UserID = jwtDecode(localStorage.getItem("token")).userId;

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            let response = await axios.get("http://localhost:3000/patientappointments", {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            let data = response.data;
            let Filterdata = data.filter((element) => {
                return element.doctor_id === UserID
            })
            setDoctorAppointmentList(Filterdata)
        } catch (error) {
            console.log("List Of Doctor " + error);
        }
    }

    const handleMedicine = async (id) => {
        Navigate(`/docotor/medicine/add/${id}`)
    }

    const handleDelete = async (id) => {

        try {
            let response = await axios.delete(`http://localhost:3000/patientappointments/delete/${id}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (response.status === 200) {
                toast.success("Deleted successfully!")
                fetchData();
            }

        } catch (error) {
            console.log("List Of Doctor " + error);
        }

    }

    const MyArr = [];
    let length = DoctorAppointmentList?.length ?? 0;
    for (let i = 0; i < length; i++) {
        const element = DoctorAppointmentList[i];

        MyArr.push(<tr key={i}>
            <td>{i + 1}</td>
            <td>{element.patientappointment_id}</td>
            <td>{element.patient_id}</td>
            <td>{element.patient_name}</td>
            <td>{element.patient_age}</td>
            <td>{element.patient_issue}</td>
            <td>
                <button className="listofpatient-table-bookBn" onClick={() => { handleMedicine(element.patientappointment_id) }}>Medicine</button>
                <button className="listofpatient-table-bookBn" onClick={() => { handleDelete(element.patientappointment_id) }}>Delete</button>
            </td>
        </tr>)
    }

    return (
        <div className="listofpatient-Container">
            <div className="listofpatient-table">
                <h3 className="listofpatient-table-title">Patient List</h3>
                <table border={1}>
                    <thead>
                        <tr>
                            <th>S No</th>
                            <th>Appointment ID</th>
                            <th>Patient ID</th>
                            <th>Patient Name</th>
                            <th>Patient Age</th>
                            <th>Issue</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {MyArr.length > 0 ? MyArr :
                            <tr key="temp">
                                <td colSpan={6} className="medicine-error-massage">No Data</td>
                            </tr>}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export { ListOfPatientAppointment, ListOfDoctorAppointment };