import React, {Component} from 'react'
import styles from "./dropzone.css";
import {httpModule as http} from "../../shared/httpModule";
import Dropzone from "react-dropzone";

class Fileupload extends Component {

    constructor(props) {
        super(props);
        this.state = this.getInitialState();
    }

    getInitialState = () => {
        return {
            files: [],
        };
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
                if (progressEvent.lengthComputable) {
                    //update progressbar here
                }
            })
        };
        this.toggleLoader();
        http.post(url, data, config).then(response => {
            this.props.onUploadFinished(true);
            this.props.onDrawingIdChange(response.data.id);
            this.toggleLoader();
        }).catch(error => {
            this.toggleLoader();
            this.props.onUploadFinished(false);
            this.props.onError({header: "Upload Error", content: error.message});
        })
    }

    onDrop(accepted, rejected) {
        if (accepted.length) {
            this.setState({
                files: accepted,
            }, () => {
                this.handleSubmit(this.state.files[0]);
            });
        }
        if (rejected.length > 0) {
            this.props.onError({header: "File type not supported", content: rejected[0].name});
        }
    }

    resetState() {
        this.setState(this.getInitialState());
    }

    render() {
        let className = styles.dropZone;
        let files = this.state.files;
        return (
            <div>
                <Dropzone onDrop={(acc, rej) => this.onDrop(acc, rej)}
                          accept="application/vnd.ms-excel, text/plain, text/csv"
                          multiple={false}>
                    {({getRootProps, getInputProps, isDragReject, isDragAccepted, isDragActive}) => {
                        className = isDragActive ? className += " " + styles.onDragActive : styles.dropZone;
                        return (
                            <div {...getRootProps()}
                                 className={className}>
                                <input {...getInputProps()}/>
                                {isDragReject ? "File type not supported" : "Drag & Drop or click to select csv file"}
                            </div>
                        )
                    }}
                </Dropzone>
                <ul>{files.map((f, index) => <li key={index}>{f.name} - {f.size} bytes</li>)}</ul>
            </div>
        );

    }
}

export default Fileupload;