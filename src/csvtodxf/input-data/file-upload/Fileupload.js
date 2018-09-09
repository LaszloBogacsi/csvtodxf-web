import React, {Component} from 'react'
import Dropzone from "react-dropzone";
import styles from "./dropzone.css";
import axios from "axios";

class Fileupload extends Component {

    constructor(props) {
        super(props);
        this.state = {
            value: '',
            files: [],
            dropzoneActive: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.onDrop = this.onDrop.bind(this);
    }

    handleSubmit(file) {
        this.props.onFileNameChange(file.name);
        let data = new FormData();
        data.append("file", file);
        const url = 'http://localhost:9090/upload-file';
        axios.post(url, data, {
            headers: {
                'Accept': 'application/json',
            },
            onUploadProgress: (progressEvent => {
                console.log(progressEvent);
                if (progressEvent.lengthComputable) {
                    //update progressbar here
                }
            })
        }).then(response => {
            this.props.onUploadFinished(true);
            this.props.onDrawingIdChange(response.data.id);
            console.log(response);
        })
        .catch(error => {
            console.log(error);
            this.props.onUploadFinished(false);
        })
    }

    handleChange(event) {
        const file = event.target.files[0];
        this.setState({value: file.name});
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

    render() {
        let className = styles.dropZone;
        if (this.state.dropzoneActive) className += ' ' + styles.onDragActive;
        return (
            <form>
                <Dropzone
                    className={className}
                    acceptClassName={styles.accepted}
                    rejectClassName={styles.rejected}
                    onDragEnter={this.onDragEnter.bind(this)}
                    onDragLeave={this.onDragLeave.bind(this)}
                    accept={"application/vnd.ms-excel, text/plain"}
                    onDrop={this.onDrop}>
                    {({isDragReject}) => isDragReject ? "File type not supported" : "Drag & Drop or click to select csv file"}

                </Dropzone>
                <ul>{this.state.files.map((f, index) => <li key={index}>{f.name} - {f.size} bytes</li>)}</ul>
            </form>
        );

    }
}

export default Fileupload;