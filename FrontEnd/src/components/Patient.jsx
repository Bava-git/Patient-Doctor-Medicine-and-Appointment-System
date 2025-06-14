import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { toast } from "sonner";
import { useNavigate, useParams } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const ListOfPatient = () => {
    const [PatientList, setPatientList] = useState([]);
    const Navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            let response = await axios.get(`http://localhost:3000/patient`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            setPatientList(response.data)
        } catch (error) {
            console.log("List Of Patient " + error);
        }
    }

    const handleUpdate = async (id) => {
        Navigate(`/patient/modifer/${id}`);
    }

    const handleDelete = async (id) => {
        try {
            let response = await axios.delete(`http://localhost:3000/patient/delete/${id}`, {
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
    let length = PatientList?.length ?? 0;
    for (let i = 0; i < length; i++) {
        const element = PatientList[i];
        MyArr.push(<tr key={i}>
            <td>{i + 1}</td>
            <td>{element.patient_id}</td>
            <td>{element.patient_name}</td>
            <td>{element.patient_age}</td>
            <td>{element.patient_place}</td>
            <td>{element.patient_email}</td>
            <td>{element.patient_contact}</td>
            <td>{element.patient_issue}</td>
            <td>
                <button className="listofpatient-table-bookBn" onClick={() => { handleUpdate(element.patient_id) }}>Update</button>
                <button className="listofpatient-table-bookBn" onClick={() => { handleDelete(element.patient_id) }}>Delete</button>
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
                            <th>Patient ID</th>
                            <th>Patient Name</th>
                            <th>Patient Age</th>
                            <th>Patient Place</th>
                            <th>Patient Email</th>
                            <th>Patient Contact</th>
                            <th>Patient Issue</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {MyArr.length > 0 ? MyArr :
                            <tr key="temp">
                                <td colSpan={9} className="medicine-error-massage">No Data</td>
                            </tr>}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

const PatientModifer = () => {

    const { update_id } = useParams();
    const Navigate = useNavigate();

    const [FormData, setFormData] = useState({
        patient_id: "",
        patient_name: "",
        patient_age: "",
        patient_place: "",
        patient_email: "",
        patient_contact: "",
        patient_issue: ""
    });
    const [Username, setUsername] = useState('');
    const [Password, setPassword] = useState('');
    const [ConfirmPassword, setConfirmPassword] = useState('');
    const PatientForm = useRef(null);

    useEffect(() => {
        if (update_id) {
            LoadOldData(update_id);
        }
    }, []);


    const LoadOldData = async (update_id) => {

        try {

            let response = await axios.get(`http://localhost:3000/patient/id/${update_id}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            setFormData({
                patient_id: response.data.patient_id,
                patient_name: response.data.patient_name,
                patient_age: response.data.patient_age,
                patient_place: response.data.patient_place,
                patient_email: response.data.patient_email,
                patient_contact: response.data.patient_contact,
                patient_issue: response.data.patient_issue,
            });

        } catch (error) {
            console.log("Patient " + error);
        }
    }

    const handleUpdate = async (e) => {
        e.preventDefault();

        const hasErrors = Object.keys(FormData).some((key) => FormData[key] === "");
        if (hasErrors) {
            toast.error("Please fill the form correctly");
            return;
        }

        try {

            let response = await axios.put(`http://localhost:3000/patient/update/${update_id}`, FormData, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (response.status === 200) {
                toast.success("Patient details updated successfully!");
                resetTheForm();
                Navigate("/patient/list");
            }

        } catch (error) {
            console.log("Patient " + error);
        }
    }


    const handleChanges = (event) => {
        const { name, value } = event.target;

        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
            patient_id: "P" + Math.floor(1000 + Math.random() * 9000),
        }));

    };


    const handleSubmit = async (e) => {
        e.preventDefault();

        const hasErrors = Object.keys(FormData).some((key) => FormData[key] === "");
        if (hasErrors) {
            toast.error("Please fill the form correctly");
            return;
        }

        if (!Password || !ConfirmPassword) {
            toast.error("Please enter password");
            return;
        } else if (Password != ConfirmPassword) {
            toast.error("Password mismatch, please update correctly");
            return;
        }

        let registerData = {
            userid: FormData.patient_id,
            username: Username,
            password: ConfirmPassword,
            user_role: "ROLE_PATIENT"
        }
        // console.log(FormData);
        // console.log(registerData);

        try {

            let registerresponse = await axios.post("http://localhost:3000/user/register", registerData, {
                headers: {
                    "Content-type": "Application/json",
                }
            })

            if (registerresponse.status === 201) {
                toast.success("Patient registered successfully!")
                const token = await registerresponse.data.token;
                localStorage.setItem("token", token);

                const decoded = jwtDecode(token);
                let role = decoded.roles;
                localStorage.setItem("role", role);
            }

            let personalresponse = await axios.post("http://localhost:3000/patient/add", FormData, {
                headers: {
                    "Content-type": "Application/json",
                }
            })


            if (personalresponse.status === 201) {
                toast.success("Patient added successfully!");
                Navigate("/appointment/list");
                resetTheForm();
            }

        } catch (error) {
            console.log("Patient " + error);
        }
    }

    const resetTheForm = () => {
        PatientForm.current.reset();
        setFormData({
            patient_id: "",
            patient_name: "",
            patient_age: "",
            patient_place: "",
            patient_email: "",
            patient_contact: "",
            patient_issue: ""
        });
        setUsername("");
        setPassword("");
        setConfirmPassword("");
    }

    return (
        <div className="patientmodifer-container">
            <h2 className="patientmodifer-container-title">New Patient Registation</h2>
            <div className="patientmodifer-formcontainer">
                <form ref={PatientForm}>
                    <div className="patientmodifer-formcontainer-seperate">
                        <div className="patientmodifer-formcontainer-personaldetails">
                            <h3>Personal Information</h3>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="patient_name">Name:</label>
                                <input type="text" name="patient_name" id="patient_name" required autoComplete="off"
                                    value={FormData.patient_name} onChange={handleChanges}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="patient_age">Age:</label>
                                <input type="text" name="patient_age" id="patient_age" required
                                    value={FormData.patient_age} onChange={handleChanges}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="patient_place">Place:</label>
                                <input type="text" name="patient_place" id="patient_place" required
                                    value={FormData.patient_place} onChange={handleChanges}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="patient_email">Email:</label>
                                <input type="email" name="patient_email" id="patient_email" required autoComplete="off"
                                    value={FormData.patient_email} onChange={handleChanges}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="patient_contact">Contact:</label>
                                <input type="text" name="patient_contact" id="patient_contact" required
                                    value={FormData.patient_contact} onChange={handleChanges}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="patient_issue">Issue:</label>
                                <input type="text" name="patient_issue" id="patient_issue" required
                                    value={FormData.patient_issue} onChange={handleChanges}
                                />
                            </div>
                        </div>
                        {!update_id &&
                            <div className="patientmodifer-formcontainer-logindetails">
                                <h3>Login Credentials</h3>
                                <div className="patientmodifer-formcontainer-field">
                                    <label htmlFor="username">Username:</label>
                                    <input type="text" name="username" id="username" required autoComplete="off"
                                        value={Username} onChange={(e) => setUsername(e.target.value)}
                                    />
                                </div>
                                <div className="patientmodifer-formcontainer-field">
                                    <label htmlFor="password">New Password:</label>
                                    <input type="password" name="password" id="password" required
                                        value={Password} onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                <div className="patientmodifer-formcontainer-field">
                                    <label htmlFor="confirmpassword">Confirm Password:</label>
                                    <input type="password" name="confirmpassword" id="confirmpassword" required
                                        value={ConfirmPassword} onChange={(e) => setConfirmPassword(e.target.value)}
                                    />
                                </div>
                            </div>}
                    </div>
                    <div className="patientmodifer-formcontainer-field">
                        <div></div>
                        {!update_id ?
                            (<button className="header-bns" onClick={(e) => { handleSubmit(e) }}>Submit</button>)
                            :
                            (<button className="header-bns" onClick={(e) => { handleUpdate(e) }}>Update</button>)
                        }
                    </div>
                </form>
            </div>
        </div>
    )
}

export { ListOfPatient, PatientModifer };