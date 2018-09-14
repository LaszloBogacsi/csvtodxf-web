import React, {Component} from 'react'
import Fileupload from "./file-upload/Fileupload";
import DrawingConfig from "./drawing-config/DrawingConfig";
import {Container} from "semantic-ui-react";

class InputData extends Component {
    constructor(props) {
        super(props);
        this.handleFileNameChange = this.handleFileNameChange.bind(this);
        this.handleDrawingIdChange = this.handleDrawingIdChange.bind(this);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);
        this.handleUploadFinished = this.handleUploadFinished.bind(this);

        this.state = {
            drawingId: '',
            fileName: '',
            uploadDone: false,
        }
    }

    handleFileNameChange(fileName) {
        this.setState({fileName: fileName})
    }

    handleDrawingIdChange(drawingId) {
        this.setState({drawingId: drawingId})
    }

    handleConvertResponse(convertResponse) {
        this.props.onConvertResponse(convertResponse);
    }

    handleUploadFinished(isDone) {
        this.setState({uploadDone: isDone});
        this.props.onProgress(2);
    }

    render() {
        let fileName = this.state.fileName;
        let drawingId = this.state.drawingId;
        let isUploadComplete = this.state.uploadDone;
        let drawingConfig;

        if (isUploadComplete) {
        // if (true) {
            drawingConfig =  <DrawingConfig fileName={fileName} drawingId={drawingId} onConvertResponse={this.handleConvertResponse}/>
        }

        return (
            <Container fluid>
                <Fileupload onUploadFinished={this.handleUploadFinished} onFileNameChange={this.handleFileNameChange} onDrawingIdChange={this.handleDrawingIdChange}/>
                {drawingConfig}
            </Container>
        );

    }
}

export default InputData;