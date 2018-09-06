import React, {Component} from 'react'
import * as Rx from "rxjs-compat";
import Dropzone from "react-dropzone";
import {Input} from "semantic-ui-react";
import styles from "./dropzone.css"

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

    handleChange(event) {
        const file = event.target.files[0];
        this.setState({value: file.name});
        this.handleSubmit(file);
    }

    handleSubmit(file) {
        this.props.onFileNameChange(file.name);
        let data = new FormData();
        data.append("file", file);
        const url = 'http://localhost:9090/upload-file';
        Rx.Observable.fromPromise(fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
            },
            body: data
        })).flatMap(res => res.json())
            .subscribe(response => {
                this.props.onDrawingIdChange(response.id);
                console.log(response);
                },
                error => console.log(error)
            )
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
                <label>Select a csv file form your computer</label>
                <Input fluid type="file" name="upload" accept=".csv, .txt"  onChange={this.handleChange}/>
                <Dropzone
                    className={className}
                    acceptClassName={styles.accepted}
                    rejectClassName={styles.rejected}
                    onDragEnter={this.onDragEnter.bind(this)}
                    onDragLeave={this.onDragLeave.bind(this)}
                    accept={"application/vnd.ms-excel, text/plain" }
                    onDrop={this.onDrop}>
                    {({isDragReject}) => isDragReject ? "File type not supported" : "Drag & Drop csv here"}

                </Dropzone>
                <ul>{this.state.files.map((f, index) => <li key={index}>{f.name} - {f.size} bytes</li>)}</ul>
            </form>
        );

    }
}

export default Fileupload;