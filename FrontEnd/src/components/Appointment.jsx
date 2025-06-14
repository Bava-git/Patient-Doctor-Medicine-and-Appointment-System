import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { toast } from "sonner";
import { format } from "date-fns";
import { jwtDecode } from "jwt-decode";

const ListOfAppointment = () => {

    const [AppointmentList, setAppointmentList] = useState([]);
    const [patientappointment_id, setpatientappointment_id] = useState("PA" + Math.floor(1000 + Math.random() * 9000));

    let UserID = jwtDecode(localStorage.getItem("token")).userId;

    useEffect(() => {
        fetchData();
    }, [])

    const safeSort = (array) => {
        if (!Array.isArray(array) || array.length === 0) {
            return [];
        }

        return array.sort((a, b) => {
            if (a.doctor_name && b.doctor_name) {
                return a.doctor_name.localeCompare(b.doctor_name);
            }
            return 0;
        });
    };

    const fetchData = async (date) => {
        try {
            let response = await axios.get("http://localhost:3000/appointment", {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            let DoctorSchedule = response.data;
            if (date) {
                DoctorSchedule = DoctorSchedule.filter((element) => {
                    return format(element.appointment_date, "yyyy-MM-dd") === date
                })
            }
            DoctorSchedule = safeSort(DoctorSchedule);
            setAppointmentList(DoctorSchedule);
        } catch (error) {
            console.log("List Of Patient " + error);
        }
    }

    const handleBooking = async (element) => {

        const selectedElement = JSON.parse(element);
        let patient_id = "";
        let patient_name = "";
        let patient_age = "";
        let patient_issue = "";

        try {
            let response = await axios.get(`http://localhost:3000/patient/id/${UserID}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            patient_id = response.data.patient_id;
            patient_name = response.data.patient_name;
            patient_age = response.data.patient_age;
            patient_issue = response.data.patient_issue;
        } catch (error) {
            console.log("List Of Patient " + error);
        }

        let appointment_datetime = selectedElement.appointment_date + "T" + selectedElement.appointment_start_time;
        let doctor_id = selectedElement.doctor_id;
        let doctor_name = selectedElement.doctor_name;
        let doctor_education = selectedElement.doctor_education;
        let doctor_specializedfield = selectedElement.doctor_specializedfield;

        const BookData = {
            patientappointment_id, appointment_datetime, patient_id, patient_name, patient_age, patient_issue,
            doctor_id, doctor_name, doctor_education, doctor_specializedfield
        }

        // console.log(BookData);

        try {
            let response = await axios.post("http://localhost:3000/patientappointments/add", BookData, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (response.status === 201) {
                toast.success("Booked successfully!")
                resetTheForm();
            }

        } catch (error) {
            console.log("List Of Patient " + error);
        }
    }

    const resetTheForm = () => {
        setpatientappointment_id("PA" + Math.floor(1000 + Math.random() * 9000));
    }

    const MyArr = [];
    let length = AppointmentList?.length ?? 0;
    for (let i = 0; i < length; i++) {
        const element = AppointmentList[i];
        MyArr.push(<tr key={i}>
            <td>{i + 1}</td>
            <td>{element.doctor_name + " " + element.doctor_education + " " + element.doctor_specializedfield}</td>
            <td>{format(element.appointment_date, "dd-MM-yyyy")}</td>
            <td>{element.appointment_start_time}</td>
            <td>{element.appointment_end_time}</td>
            <td>
                <button className="listofpatient-table-bookBn" onClick={() => { handleBooking(JSON.stringify(element)) }}>Book</button>
            </td>
        </tr>)
    }

    return (
        <div className="listofpatient-Container">
            <div className="listofpatient-table">
                <h3 className="listofpatient-table-title">Doctor Schedule</h3>
                <div className="appointment-date">
                    <p className="appointment-date-title">Search by date</p>
                    <input type="date" name="" id="" onChange={(e) => { fetchData(e.target.value) }} />
                </div>
                <table border={1}>
                    <thead>
                        <tr>
                            <th>S No</th>
                            <th>Doctor Name</th>
                            <th>Date</th>
                            <th>From</th>
                            <th>To</th>
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

const AppointmentModifer = () => {

    const [appointment_id, setappointment_id] = useState("A" + Math.floor(1000 + Math.random() * 9000));
    const [appointment_date, setappointment_date] = useState('');
    const [appointment_start_time, setappointment_start_time] = useState('');
    const [appointment_end_time, setappointment_end_time] = useState('');
    const [doctor_id, setdoctor_id] = useState('');
    const [doctor_name, setdoctor_name] = useState('');
    const [doctor_education, setdoctor_education] = useState('');
    const [doctor_specializedfield, setdoctor_specializedfield] = useState('');

    const AppointmentForm = useRef(null);
    const [DoctorList, setDoctorList] = useState([]);

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            let response = await axios.get("http://localhost:3000/doctor", {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            setDoctorList(response.data)
        } catch (error) {
            console.log("List Of Doctor " + error);
        }
    }

    const handleSelect = (e) => {
        const selectedDoctor = JSON.parse(e.target.value); // Convert string back to object
        setdoctor_id(selectedDoctor.doctor_id);
        setdoctor_name(selectedDoctor.doctor_name);
        setdoctor_education(selectedDoctor.doctor_education);
        setdoctor_specializedfield(selectedDoctor.doctor_specializedfield);
    };


    const handleSubmit = async (e) => {
        e.preventDefault();

        let FormData = {
            appointment_id, appointment_date, appointment_start_time, appointment_end_time,
            doctor_id, doctor_name, doctor_education, doctor_specializedfield
        }

        if (!appointment_date && !appointment_start_time && !appointment_end_time
            && !doctor_id && !doctor_name && !doctor_education && !doctor_specializedfield) {
            toast.error("Please fill the form correctly");
            return;
        }

        try {

            let response = await axios.post("http://localhost:3000/appointment/add", FormData, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            if (response.status === 201) {
                toast.success("Doctor schedule added successfully!")
                resetTheForm();
            }
        } catch (error) {
            console.log("Appointment " + error);
        }
    }

    const resetTheForm = () => {
        AppointmentForm.current.reset();
        setappointment_id("A" + Math.floor(1000 + Math.random() * 9000));
        setappointment_date("");
        setappointment_start_time("");
        setappointment_end_time("");
        setdoctor_id("");
        setdoctor_name("");
        setdoctor_education("");
        setdoctor_specializedfield("");
    }

    return (
        <div className="patientmodifer-container">
            <h2 className="patientmodifer-container-title">New Appointment Registation</h2>
            <div className="patientmodifer-formcontainer">
                <form ref={AppointmentForm}>
                    <div className="patientmodifer-formcontainer-seperate">
                        <div className="patientmodifer-formcontainer-personaldetails">
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="doctorname">Doctor Name:</label>
                                <select name="doctor_id" id="doctorname" onChange={handleSelect} >
                                    <option value="">Select Doctor</option>
                                    {DoctorList.map((element) => (
                                        <option key={element.doctor_id} value={JSON.stringify(element)}>
                                            {element.doctor_name + " " + element.doctor_education + " " + element.doctor_specializedfield}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="age">Date:</label>
                                <input type="date" name="appointment_date" id="age" required
                                    value={appointment_date} onChange={(e) => { setappointment_date(e.target.value) }}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="place">Start Time:</label>
                                <input type="time" name="appointment_start_time" id="place" required
                                    value={appointment_start_time} onChange={(e) => { setappointment_start_time(e.target.value) }}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="email">End Time:</label>
                                <input type="time" name="appointment_end_time" id="email" required
                                    value={appointment_end_time} onChange={(e) => { setappointment_end_time(e.target.value) }}
                                />
                            </div>
                        </div>
                    </div>
                    <div className="patientmodifer-formcontainer-field">
                        <div></div>
                        <button className="header-bns" onClick={(e) => { handleSubmit(e) }}>Submit</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export { ListOfAppointment, AppointmentModifer };