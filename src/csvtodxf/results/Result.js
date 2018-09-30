import React, {Component} from 'react'
import Download from "./Download";
import {Table} from "semantic-ui-react";

class Result extends Component {

    render() {
        let onProgress = this.props.onProgress;
        const response = this.props.convertResponse;
        return (
            <div>
                <h2>Conversion Results</h2>
                <Table basic='very' celled collapsing>

                    <Table.Body>
                        <Table.Row>
                            <Table.Cell>Duration</Table.Cell>
                            <Table.Cell>{response.durationInMillies < 1000 ? `${response.durationInMillies} ms` : `Math.round(response.durationInMillies / 1000) sec`}</Table.Cell>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>File size</Table.Cell>
                            <Table.Cell>{response.fileSize} kb</Table.Cell>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>Lines converted</Table.Cell>
                            <Table.Cell>{response.numberOfLinesConverted}</Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
                <Download downloadId={response.downloadId} onProgress={onProgress}/>
            </div>

        )
    }
}

export default Result;