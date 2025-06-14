import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { toast } from "sonner";
import { useParams } from "react-router-dom";
import { format } from "date-fns";
import { jwtDecode } from "jwt-decode";

const MedicineManager = () => {

    const { patientappointmentID } = useParams();

    const [DisplayDetails, setDisplayDetails] = useState([]);
    const [MedicationList, setMedicationList] = useState([]);
    const [medicine_id, setmedicine_id] = useState("M" + Math.floor(1000 + Math.random() * 9000));
    const [medicine_name, setmedicine_name] = useState('');
    const [medicine_afterfood, setmedicine_afterfood] = useState(true);
    const [medicine_Morning, setmedicine_Morning] = useState(false);
    const [medicine_Afternoon, setmedicine_Afternoon] = useState(false);
    const [medicine_night, setmedicine_night] = useState(false);
    const [medicine_days, setmedicine_days] = useState("");
    const MedicineForm = useRef(null);

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            let response = await axios.get(`http://localhost:3000/patientappointments/id/${patientappointmentID}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            setDisplayDetails(response.data);

            let PatientAppointmentresponse = await axios.get(`http://localhost:3000/medication/patientappointmentid/${patientappointmentID}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            setMedicationList(PatientAppointmentresponse.data);
        } catch (error) {
            console.log("List Of Doctor " + error);
        }

    }

    const handleMedicineUpdate = async (id) => {

        try {
            let response = await axios.get(`http://localhost:3000/medication/id/${id}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            let UpdateMedicine = response.data;
            setmedicine_id(UpdateMedicine.medicine_id);
            setmedicine_name(UpdateMedicine.medicine_name);
            setmedicine_Morning(UpdateMedicine.medicine_Morning);
            setmedicine_Afternoon(UpdateMedicine.medicine_Afternoon);
            setmedicine_night(UpdateMedicine.medicine_night);
            setmedicine_afterfood(UpdateMedicine.medicine_afterfood);
            setmedicine_days(UpdateMedicine.medicine_days);

        } catch (error) {
            console.log("List Of Doctor " + error);
        }

    }

    const handleMedicineDelete = async (id) => {
        try {
            let response = await axios.delete(`http://localhost:3000/medication/delete/${id}`, {
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
    let length = MedicationList?.length ?? 0;
    for (let i = 0; i < length; i++) {
        const element = MedicationList[i];
        MyArr.push(<tr key={i}>
            <td>{i + 1}</td>
            <td>{element.medicine_name}</td>
            <td>{element.medicine_Morning ? "✓" : ""}</td>
            <td>{element.medicine_Afternoon ? "✓" : ""}</td>
            <td>{element.medicine_night ? "✓" : ""}</td>
            <td>{element.medicine_afterfood ? "After Food" : "Before Food"}</td>
            <td>{element.medicine_days}</td>
            <td>
                <button className="listofpatient-table-bookBn" onClick={() => {
                    handleMedicineUpdate(element.medicine_id)
                }}>Update</button>
                <button className="listofpatient-table-bookBn" onClick={() => {
                    handleMedicineDelete(element.medicine_id)
                }}>Delete</button>
            </td>
        </tr>)
    }

    const handleAddMedicine = async (e) => {
        e.preventDefault();

        let patientappointment_id = patientappointmentID;
        let appointment_datetime = "";
        let patient_id = "";
        let patient_name = "";
        let doctor_id = "";
        let doctor_name = "";


        try {
            let response = await axios.get(`http://localhost:3000/patientappointments/id/${patientappointmentID}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            appointment_datetime = response.data.appointment_datetime;
            patient_id = response.data.patient_id;
            patient_name = response.data.patient_name;
            doctor_id = response.data.doctor_id;
            doctor_name = response.data.doctor_name;
        } catch (error) {
            console.log("List Of Doctor " + error);
        }

        let sendData = {
            medicine_id,
            patientappointment_id,
            appointment_datetime,
            patient_id,
            patient_name,
            doctor_id,
            doctor_name,
            medicine_name,
            medicine_Morning,
            medicine_Afternoon,
            medicine_night,
            medicine_afterfood,
            medicine_days,
        }

        // console.log(sendData);

        let isEmpty = Object.keys(sendData).filter(key => {
            !sendData[key];
            // console.log(!sendData[key]);
        });
        if (isEmpty.length > 0) {
            toast.error("please fill the form correctly");
            return;
        }

        // console.log(sendData);

        try {
            let response = await axios.post("http://localhost:3000/medication/add", sendData, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (response.status === 201) {
                toast.success("Added successfully!");
                fetchData();
                resetTheForm();
            }
        } catch (error) {
            console.log("List Of Doctor " + error);
        }

    }

    const resetTheForm = () => {
        MedicineForm.current.reset();
        setmedicine_name("");
        setmedicine_id("M" + Math.floor(1000 + Math.random() * 9000));
        setmedicine_Morning(false);
        setmedicine_Afternoon(false);
        setmedicine_night(false);
        setmedicine_afterfood(true);
        setmedicine_days("");
    }


    return (
        <div className="medicine-Container">
            <h2 className="medicine-title">Medicine Manager</h2>
            <div className="medicine-table">
                <div className="medicine-detailscontainer">
                    <div className="medicine-details">
                        <div className="medicine-table-field">
                            <p>Appointment ID</p>
                            <p>{patientappointmentID}</p>
                        </div>
                        <div className="medicine-table-field">
                            <p>Appointment Date/Time</p>
                            <p>{DisplayDetails?.appointment_datetime ?
                                format(new Date(DisplayDetails.appointment_datetime), "dd-MMM-yyyy hh:mm a")
                                : ""}</p>
                        </div>
                    </div>
                    <div className="medicine-details">
                        <div className="medicine-table-field">
                            <p>Patient ID</p>
                            <p>{DisplayDetails.patient_id}</p>
                        </div>
                        <div className="medicine-table-field">
                            <p>Patient Name</p>
                            <p>{DisplayDetails.patient_name}</p>
                        </div>
                    </div>
                    <div className="medicine-details">
                        <div className="medicine-table-field">
                            <p>Doctor ID</p>
                            <p>{DisplayDetails.doctor_id}</p>
                        </div>
                        <div className="medicine-table-field">
                            <p>Doctor Name</p>
                            <p>{DisplayDetails.doctor_name}</p>
                        </div>
                    </div>
                </div>
                <h3 className="medicine-table-title">Medicine List</h3>
                <table border={1}>
                    <thead>
                        <tr>
                            <th>S No</th>
                            <th>Medicine Name</th>
                            <th>Morning</th>
                            <th>After noon</th>
                            <th>Night</th>
                            <th>Food Intake</th>
                            <th>Days</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {MyArr.length > 0 ? MyArr :
                            <tr key="temp">
                                <td colSpan={8} className="medicine-error-massage">No Data</td>
                            </tr>}
                    </tbody>
                </table>
            </div>
            <div className="medicine-add">
                <h3 className="medicine-add-title">Add Medicine</h3>
                <form action="" ref={MedicineForm}>
                    <div className="medicine-add-field">
                        <label htmlFor="medicinename">Medication Name:</label>
                        <input type="text" name="medicinename" id="medicinename" value={medicine_name} onChange={(e) => setmedicine_name(e.target.value)} />
                    </div>
                    <div className="medicine-add-field">
                        <label htmlFor="morning">Daily Medication Routine:</label>
                        <div className="medicine-add-routine">
                            <input type="checkbox" name="morning" id="morning" checked={medicine_Morning} onChange={(e) => {
                                if (e.target.checked) {
                                    setmedicine_Morning(true);
                                } else {
                                    setmedicine_Morning(false);
                                }
                            }} />
                            <label htmlFor="morning">Morning</label>
                            <input type="checkbox" name="afnun" id="afnun" checked={medicine_Afternoon} onChange={(e) => {
                                if (e.target.checked) {
                                    setmedicine_Afternoon(true);
                                } else {
                                    setmedicine_Afternoon(false);
                                }
                            }} />
                            <label htmlFor="afnun">After noon</label>
                            <input type="checkbox" name="night" id="night" checked={medicine_night} onChange={(e) => {
                                if (e.target.checked) {
                                    setmedicine_night(true);
                                } else {
                                    setmedicine_night(false);
                                }
                            }} />
                            <label htmlFor="night">Night</label>
                        </div>
                    </div>
                    <div className="medicine-add-field">
                        <label htmlFor="before">Food Intake:</label>
                        <div className="medicine-add-routine">
                            <input type="radio" name="intake" id="before" checked={!medicine_afterfood}
                                onChange={(e) => { setmedicine_afterfood(false) }} />
                            <label htmlFor="before">Before</label>
                            <input type="radio" name="intake" id="after" checked={medicine_afterfood}
                                onChange={(e) => { setmedicine_afterfood(true) }} />
                            <label htmlFor="after">After</label>
                        </div>
                    </div>
                    <div className="medicine-add-field">
                        <label htmlFor="medicinedays">Medication Days:</label>
                        <input type="text" name="medicinedays" id="medicinedays" value={medicine_days}
                            onChange={(e) => setmedicine_days(e.target.value)} />
                    </div>
                    <div className="medicine-add-field">
                        <div></div>
                        <button className="header-bns" onClick={(e) => { handleAddMedicine(e) }}>Add</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

const MedicineManagerPatientSide = () => {

    const [MedicationList, setMedicationList] = useState([]);
    let UserID = jwtDecode(localStorage.getItem("token")).userId;

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            let response = await axios.get("http://localhost:3000/medication", {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            let MedicationForPatient = response.data;
            MedicationForPatient = MedicationForPatient.filter((element) => {
                return element.patient_id === UserID
            })
            setMedicationList(MedicationForPatient);
        } catch (error) {
            console.log("List Of Doctor " + error);
        }
    }

    const MyArr = [];
    let length = MedicationList?.length ?? 0;
    for (let i = 0; i < length; i++) {
        const element = MedicationList[i];
        MyArr.push(<tr key={i}>
            <td>{i + 1}</td>
            <td>{element.medicine_name}</td>
            <td>{element.medicine_Morning ? "✓" : ""}</td>
            <td>{element.medicine_Afternoon ? "✓" : ""}</td>
            <td>{element.medicine_night ? "✓" : ""}</td>
            <td>{element.medicine_afterfood ? "After Food" : "Before Food"}</td>
            <td>{element.medicine_days}</td>
        </tr>)
    }

    return (
        <div className="medicine-Container">
            <h2 className="medicine-title">Medicine Log</h2>
            {MedicationList.map((element, index) =>
                <div className="medicine-table" key={index} >
                    <div className="medicine-detailscontainer">
                        <div className="medicine-details">
                            <div className="medicine-table-field">
                                <p>Appointment ID</p>
                                <p>{element.patientappointment_id}</p>
                            </div>
                            <div className="medicine-table-field">
                                <p>Appointment Date/Time</p>
                                <p>{format(new Date(element.appointment_datetime), "dd-MMM-yyyy hh:mm a")}</p>
                            </div>
                        </div>
                        <div className="medicine-details">
                            <div className="medicine-table-field">
                                <p>Patient ID</p>
                                <p>{element.patient_id}</p>
                            </div>
                            <div className="medicine-table-field">
                                <p>Patient Name</p>
                                <p>{element.patient_name}</p>
                            </div>
                        </div>
                        <div className="medicine-details">
                            <div className="medicine-table-field">
                                <p>Doctor ID</p>
                                <p>{element.doctor_id}</p>
                            </div>
                            <div className="medicine-table-field">
                                <p>Doctor Name</p>
                                <p>{element.doctor_name}</p>
                            </div>
                        </div>
                    </div>
                    <h3 className="medicine-table-title">Medicine List</h3>
                    <table border={1}>
                        <thead>
                            <tr>
                                <th>S No</th>
                                <th>Medicine Name</th>
                                <th>Morning</th>
                                <th>After noon</th>
                                <th>Night</th>
                                <th>Food Intake</th>
                                <th>Days</th>
                            </tr>
                        </thead>
                        <tbody>
                            {MyArr.length > 0 ? MyArr :
                                <tr key="temp">
                                    <td colSpan={8} className="medicine-error-massage">No Medicine</td>
                                </tr>}
                        </tbody>
                    </table>
                </div>)}
        </div >
    )
}

export { MedicineManager, MedicineManagerPatientSide };