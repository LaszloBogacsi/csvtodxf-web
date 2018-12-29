import React, {Component} from 'react'
import Dropzone from "react-dropzone";
import styles from "./dropzone.css";
import {httpModule as http} from "../../shared/httpModule";

class Fileupload extends Component {

    constructor(props) {
        super(props);
        this.state = this.getInitialState();

        this.handleChange = this.handleChange.bind(this);
        this.onDrop = this.onDrop.bind(this);
    }

    getInitialState = () => {
        const initialState = {
            files: [],
            dropzoneActive: false
        };
        return initialState;
    };

    toggleLoader() {
        this.props.onToggleLoader();
    }

    handleSubmit(file) {
        this.props.onFileNameChange(file.name);
        this.props.onError({});
        let data = new FormData();
        data.append("file", file);
        const url = '/upload-file';
        let config = {
            headers: {
                'Accept': 'application/json',
            },
            onUploadProgress: (progressEvent => {
                console.log(progressEvent);
                if (progressEvent.lengthComputable) {
                    //update progressbar here
                }
            })
        };
        this.toggleLoader();
        http.post(url, data, config).then(response => {
            this.props.onUploadFinished(true);
            this.props.onDrawingIdChange(response.data.id);
            console.log(response);
            this.toggleLoader();
        }).catch(error => {
            console.log(error);
            this.toggleLoader();
            this.props.onUploadFinished(false);
            this.props.onError({header: "Upload Error", content: error.message});
        })
    }

    handleChange(event) {
        const file = event.target.files[0];
        this.handleSubmit(file);
    }

    onDragEnter() {
        this.setState({
            dropzoneActive: true
        });
    }

    onDragLeave() {
        this.setState({
            dropzoneActive: false
        });
    }

    onDrop(accepted, rejected) {
        this.setState({
            files: accepted,
            dropzoneActive: false
        });
        if (rejected.length > 0) {
            console.log("this filetype is not supported: " + rejected[0].name);
        }
        const oneFile = this.state.files[0];
        if (oneFile) this.handleSubmit(oneFile);
    }

    resetState() {
        this.setState(this.getInitialState());
    }

    render() {
        let className = styles.dropZone;
        let files = this.state.files;
        if (this.state.dropzoneActive) className += ' ' + styles.onDragActive;
        return (
            <form>
                <Dropzone
                    className={className}
                    acceptClassName={styles.accepted}
                    rejectClassName={styles.rejected}
                    onDragEnter={this.onDragEnter.bind(this)}
                    onDragLeave={this.onDragLeave.bind(this)}
                    accept={"application/vnd.ms-excel, text/plain, text/csv"}
                    onDrop={this.onDrop}>
                    {({isDragReject}) => isDragReject ? "File type not supported" : "Drag & Drop or click to select csv file"}

                </Dropzone>
                <ul>{files.map((f, index) => <li key={index}>{f.name} - {f.size} bytes</li>)}</ul>
            </form>
        );

    }
}

export default Fileupload;