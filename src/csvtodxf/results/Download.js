import React, {Component} from 'react'
import {Button} from "semantic-ui-react";

class Download extends Component {
    constructor(props) {
        super(props);
        this.handleOnclick = this.handleOnclick.bind(this);
    }

    handleOnclick() {
        const id = this.props.downloadId;
        const url = `http://localhost:9090/download/${id}`;
        this.props.onProgress(4);
        window.location.href = url;
    }


    render() {
        return (
            <div>
                <Button onClick={this.handleOnclick}>Download</Button>
            </div>
        );

    }
}

export default Download;